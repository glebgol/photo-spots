package com.glebgol.photospots.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glebgol.photospots.domain.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val tagsRepository: TagRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    fun loadMarkers() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = false,
                    markers = tagsRepository.getAllTags()
                )
            }
        }
    }
}
