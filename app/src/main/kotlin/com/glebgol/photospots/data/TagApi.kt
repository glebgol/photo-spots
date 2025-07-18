package com.glebgol.photospots.data

import com.glebgol.photospots.data.dto.TagDetailsDto
import com.glebgol.photospots.data.dto.TagDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TagApi {
    @GET("/tags")
    suspend fun getTags(): List<TagDto>

    @GET("/tags/{id}")
    suspend fun getTagDetails(@Path("id") id: String): TagDetailsDto
}
