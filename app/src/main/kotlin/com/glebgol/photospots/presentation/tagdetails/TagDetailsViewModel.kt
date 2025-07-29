package com.glebgol.photospots.presentation.tagdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glebgol.photospots.domain.TagRepository
import com.glebgol.photospots.presentation.AndroidMapNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagDetailsViewModel @Inject constructor(
    private val tagsRepository: TagRepository,
    private val mapNavigator: AndroidMapNavigator
): ViewModel() {
    private val _state = MutableStateFlow(TagDetailsState())
    val state = _state.asStateFlow()

    init {
        Log.d("ViewModel", "DetailViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel", "DetailViewModel cleared")
    }

    fun onIntent(intent: TagDetailsIntent) {
        when (intent) {
            is TagDetailsIntent.LoadTagDetailsIntent -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                    tagsRepository.getTagDetails(intent.tagId)
                        .onSuccess { tagDetails ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    tagDetails = tagDetails,
                                    isFavourite = tagDetails.isFavourite
                                )
                            }
                        }
                        .onFailure {
                            _state.update {
                                it.copy(isError = true, isLoading = false)
                            }
                        }
                }
            }
            is TagDetailsIntent.ToggleLikeIntent -> {
                viewModelScope.launch {
                    tagsRepository.updateTag(tagId = intent.id, isFavourite = intent.isFavourite)
                    _state.update {
                        it.copy(isFavourite = intent.isFavourite)
                    }
                }
            }

            is TagDetailsIntent.OpenTagOnMap -> {
                mapNavigator.openMap(latitude = intent.latitude, longitude = intent.longitude)
            }
        }
    }
}
