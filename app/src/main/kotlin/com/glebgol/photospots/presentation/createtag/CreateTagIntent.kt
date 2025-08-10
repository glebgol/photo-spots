package com.glebgol.photospots.presentation.createtag

sealed interface CreateTagIntent {
    object OnCreate: CreateTagIntent

    class OnDescriptionChangeIntent(val description: String): CreateTagIntent
}
