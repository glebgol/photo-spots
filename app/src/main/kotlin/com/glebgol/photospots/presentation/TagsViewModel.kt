package com.glebgol.photospots.presentation

import android.nfc.Tag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glebgol.photospots.domain.GetTagsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TagsViewModel(
    private val getTagsUseCase: GetTagsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(TagsState())
    val state = _state.asStateFlow()

    fun onIntent(intent: TagsIntent) {
        when(intent) {
            TagsIntent.LoadTagsIntent -> {
                _state.update {
                    it.copy(isLoading = true)
                }
                viewModelScope.launch {
                    _state.update {
                        it.copy(tags = getTagsUseCase.getTags(), isLoading = false)
                    }
                }
            }
        }
    }
}
