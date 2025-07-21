package com.glebgol.photospots.presentation.tagdetails

sealed interface TagDetailsIntent {
    data class LoadTagDetailsIntent(val tagId: String) : TagDetailsIntent
    data class ToggleLikeIntent(val id: String, val isFavourite: Boolean): TagDetailsIntent
}
