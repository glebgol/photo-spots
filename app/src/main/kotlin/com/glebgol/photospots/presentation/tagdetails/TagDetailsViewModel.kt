package com.glebgol.photospots.presentation.tagdetails

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
class TagDetailsViewModel @Inject constructor(
    private val tagsRepository: TagRepository
): ViewModel() {
    private val _state = MutableStateFlow(TagDetailsState())
    val state = _state.asStateFlow()

    fun onIntent(intent: TagDetailsIntent) {
        when (intent) {
            is TagDetailsIntent.LoadTagDetailsIntent -> {
                _state.update {
                    it.copy(isLoading = true)
                }
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            tagDetails = tagsRepository.getTagDetails(intent.tagId)
                        )
                    }
                }
            }
            is TagDetailsIntent.ToggleLikeIntent -> {

            }
        }
    }
}
