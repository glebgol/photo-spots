package com.glebgol.photospots.data

import retrofit2.http.GET

interface TagApi {
    @GET("/tags")
    suspend fun getTags(): List<TagDto>
}
