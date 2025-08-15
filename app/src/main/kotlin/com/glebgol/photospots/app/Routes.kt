package com.glebgol.photospots.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object TagsListRoute : Route

    @Serializable
    data class TagDetailsRoute(val id: String) : Route

    @Serializable
    data object Graph : Route

    @Serializable
    data object Create : Route

    @Serializable
    data object Map : Route
}
