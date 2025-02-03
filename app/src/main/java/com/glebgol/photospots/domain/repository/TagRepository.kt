package com.glebgol.photospots.domain.repository

import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.data.TagDetails
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Path
import java.io.File

interface TagRepository {

    fun getTags(): Single<List<Tag>>
    fun getTagById(id: Long): Single<TagDetails>
    fun createTag(
        image: File, description: String, longitude: Double, latitude: Double
    ): Single<TagDetails>
}