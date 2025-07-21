package com.glebgol.photospots.presentation

import com.glebgol.photospots.domain.TagData

data class TagsState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
    val searchQuery: String = "",
    val tags: List<TagData> = emptyList(),
    val searchResultTags: List<TagData> = emptyList(),
    val favoriteTags: List<TagData> = emptyList(),
    val selectedTabIndex: Int = 0,
    val errorMessage: String? = null
)
