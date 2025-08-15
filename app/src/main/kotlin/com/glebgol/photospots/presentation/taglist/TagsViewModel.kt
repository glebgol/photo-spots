package com.glebgol.photospots.presentation.taglist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glebgol.photospots.domain.usecases.GetTagsUseCase
import com.glebgol.photospots.domain.usecases.GetFavouriteTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModel @Inject constructor(
    private val getTagsUseCase: GetTagsUseCase,
    private val getFavouriteTagsUseCase: GetFavouriteTagsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TagsState())
    val state = _state.asStateFlow()

    init {
        Log.d("ViewModel", "List created")
        loadTags()
        observeFavoriteTags()
        observeSearchQueryChanges()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQueryChanges() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResultTags = emptyList()
                            )
                        }
                    }

                    else -> {
                        searchJob?.cancel()
                        searchJob = searchTags(query = query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchTags(query: String): Job {
        return viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            getTagsUseCase.execute(query = query)
                .onSuccess { tags ->
                    _state.update {
                        it.copy(
                            searchResultTags = tags,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private var searchJob: Job? = null

    private fun observeFavoriteTags() {
        getFavouriteTagsUseCase.execute()
            .onSuccess { tagsFlow ->
                tagsFlow.onEach { tags ->
                    _state.update {
                        it.copy(favoriteTags = tags)
                    }
                }.launchIn(viewModelScope)
            }
    }

    fun onIntent(intent: TagsIntent) {
        when (intent) {
            TagsIntent.LoadTagsIntent -> {
                loadTags()
            }

            is TagsIntent.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = intent.query)
                }
            }
            is TagsIntent.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = intent.index)
                }
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
