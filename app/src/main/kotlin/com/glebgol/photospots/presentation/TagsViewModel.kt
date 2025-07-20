package com.glebgol.photospots.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glebgol.photospots.domain.GetTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModel @Inject constructor(
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TagsState())
    val state = _state.asStateFlow()

    init {
        Log.d("ViewModel", "List created")
        loadTags()
    }

    fun onIntent(intent: TagsIntent) {
        when (intent) {
            TagsIntent.LoadTagsIntent -> {
                loadTags()
            }
        }
    }

    private fun loadTags() {
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            getTagsUseCase.execute()
                .onFailure {
                    _state.update {
                        it.copy(isError = true)
                    }
                }
                .onSuccess { tags ->
                    _state.update {
                        it.copy(tags = tags, isLoading = false, isError = false)
                    }
                }
        }
    }
}
