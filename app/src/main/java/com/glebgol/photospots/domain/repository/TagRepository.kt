package com.glebgol.photospots.domain.repository

import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.data.TagDetails
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Path

interface TagRepository {

    suspend fun getTags(): List<Tag>
    suspend fun getTagById(id: Long): TagDetails
    suspend fun createTag(
        image: MultipartBody.Part, description: String, longitude: Double, latitude: Double
    ): TagDetails
}