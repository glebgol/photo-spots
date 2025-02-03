package com.glebgol.photospots.data

import com.glebgol.photospots.domain.client.ApiClient
import com.glebgol.photospots.domain.data.Tag
import com.glebgol.photospots.domain.data.TagDetails
import com.glebgol.photospots.domain.repository.TagRepository
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class DefaultTagRepository : TagRepository {

    override fun getTags(): Single<List<Tag>> {
        return Single.create { emitter ->
            try {
                val response = ApiClient.tagApi.getTags().execute()
                if (response.isSuccessful) {
                    emitter.onSuccess(response.body() ?: emptyList())
                } else {
                    emitter.onError(Exception("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun getTagById(id: Long): Single<TagDetails> {
        return Single.create { emitter ->
            try {
                val response = ApiClient.tagApi.getTagById(id).execute()
                if (response.isSuccessful) {
                    emitter.onSuccess(response.body()!!)
                } else {
                    emitter.onError(
                        Exception("Error while getting tag details: ${response.code()}")
                    )
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun createTag(image: File, description: String, longitude: Double, latitude: Double)
    : Single<TagDetails> {
        return Single.create { emitter ->
            try {
                val response = ApiClient.tagApi.createTag(image = getImagePart(image),
                    description = description, longitude = longitude, latitude = latitude).execute()
                if (response.isSuccessful) {
                    emitter.onSuccess(response.body()!!)
                } else {
                    emitter.onError(
                        Exception("Error while creating tag ${response.code()}")
                    )
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    companion object {
        private fun getImagePart(image: File): MultipartBody.Part {
            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), image)
            val imagePart = MultipartBody.Part.createFormData("image", image.name, requestFile)
            return imagePart
        }
    }
}
