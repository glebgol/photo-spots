package com.glebgol.photospots.data

import com.glebgol.photospots.data.dto.TagDetailsDto
import com.glebgol.photospots.data.dto.TagDto
import com.glebgol.photospots.domain.TagData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface TagApi {
    @GET("/tags")
    suspend fun getTags(): List<TagDto>

    @GET("/tags/{id}")
    suspend fun getTagDetails(@Path("id") id: String): TagDetailsDto

    @GET("/tags/search")
    suspend fun getTagsByQuery(@Query("query") query: String): List<TagDto>

    @Multipart
    @POST("tags")
    suspend fun createTag(
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("longitude") longitude: Double,
        @Part("latitude") latitude: Double
    ): TagDetailsDto
}
