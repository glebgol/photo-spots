package com.glebgol.photospots.domain

import com.glebgol.photospots.domain.data.Tag
import retrofit2.Call
import retrofit2.http.GET

interface TagApi {

    @GET("tags")
    fun getTags(): Call<List<Tag>>
}
