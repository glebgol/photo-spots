package com.glebgol.photospots.presentation.createtag

import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glebgol.photospots.domain.CreateTagData
import com.glebgol.photospots.domain.TagRepository
import com.glebgol.photospots.presentation.DefaultLocationClient
import com.glebgol.photospots.presentation.LocationClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTagViewModel @Inject constructor(
    private val photoTaker: CameraXPhotoTaker,
    private val tagRepository: TagRepository,
    private val locationClient: LocationClient
) : ViewModel() {

    private val _state = MutableStateFlow(CreateTagState())
    val state: StateFlow<CreateTagState> = _state.asStateFlow()

    private val _bitmap: MutableStateFlow<Bitmap?> = MutableStateFlow(null)
    val bitmap = _bitmap.asStateFlow()

    fun onIntent(intent: CreateTagIntent) {
        when (intent) {
            CreateTagIntent.OnCreate -> createTag()
            is CreateTagIntent.OnDescriptionChangeIntent -> {
                _state.update { it.copy(description = intent.description) }
            }
        }
    }

    private fun createTag() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            bitmap.value?.let { image ->
                val location = locationClient.getLocation()
                val createTagData = CreateTagData(
                    image = image,
                    description = state.value.description,
                    latitude = location.latitude,
                    longitude = location.longitude
                )

                tagRepository.createTag(createTagData)
                    .onSuccess {
                        _state.update {
                            it.copy(isSuccess = true)
                        }
                    }
                    .onFailure {
                        _state.update {
                            it.copy(isFailure = true, isLoading = false)
                        }
                    }
            }
        }
    }

    fun onTakePhoto(bitmap: Bitmap) {
        _bitmap.value = bitmap
        _state.update {
            it.copy(isCameraOpen = false)
        }
    }

    fun takePhoto(controller: LifecycleCameraController) {
        photoTaker.takePhoto(controller, ::onTakePhoto)
    }
}
