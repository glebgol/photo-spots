package com.glebgol.photospots.presentation

sealed class TagsIntent {

    object LoadTagsIntent: TagsIntent()
    data class OnSearchQueryChange(val query: String): TagsIntent()
    data class OnTabSelected(val index: Int): TagsIntent()
}
