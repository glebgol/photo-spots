package com.glebgol.photospots.data

import com.glebgol.photospots.data.dto.TagDetailsDto
import com.glebgol.photospots.data.dto.TagDto
import com.glebgol.photospots.domain.TagData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TagApi {
    @GET("/tags")
    suspend fun getTags(): List<TagDto>

    @GET("/tags/{id}")
    suspend fun getTagDetails(@Path("id") id: String): TagDetailsDto

    @GET("/tags/search")
    suspend fun getTagsByQuery(@Query("query") query: String): List<TagDto>
}
