package com.glebgol.photospots.domain.client

import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.data.TagDetails
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface TagApi {

    @GET("tags")
    fun getTags(): Call<List<Tag>>

    @Multipart
    @POST("tags")
    fun createTag(
        @Part image: MultipartBody.Part,
        @Part("description") description: String,
        @Part("longitude") longitude: Double,
        @Part("latitude") latitude: Double
    ): Call<TagDetails>
}
