package com.glebgol.photospots.data.retrofit

import android.graphics.Bitmap
import com.glebgol.photospots.data.TagApi
import com.glebgol.photospots.data.TagsRemoteDataSource
import com.glebgol.photospots.data.dto.TagDetailsDto
import com.glebgol.photospots.data.dto.TagDto
import com.glebgol.photospots.domain.CreateTagData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class RetrofitTagsRemoteDataSource @Inject constructor(
    private val tagsApi: TagApi
): TagsRemoteDataSource {
    override suspend fun getTags(): List<TagDto> {
        return tagsApi.getTags()
    }

    override suspend fun getTagDetails(id: String): TagDetailsDto {
        return tagsApi.getTagDetails(id = id)
    }

    override suspend fun getTagsByQuery(query: String): List<TagDto> {
        return tagsApi.getTagsByQuery(query = query)
    }

    override suspend fun createTag(tag: CreateTagData): TagDetailsDto {
        return tagsApi.createTag(
            image = MultipartBody.Part.createFormData(name = "image",
                filename = "image${System.currentTimeMillis()}",
                body = tag.image.toRequestBody()
            ),
            description = tag.description.toRequestBody("text/plain".toMediaTypeOrNull()),
            longitude = tag.longitude,
            latitude = tag.latitude
        )
    }
}

private fun Bitmap.toRequestBody(format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, quality: Int = 80): RequestBody {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(format, quality, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return byteArray.toRequestBody("image/*".toMediaTypeOrNull())
}
