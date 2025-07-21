package com.glebgol.photospots.data.retrofit

import android.nfc.Tag
import com.glebgol.photospots.data.TagApi
import com.glebgol.photospots.data.dto.TagDetailsDto
import com.glebgol.photospots.data.dto.TagDto
import com.glebgol.photospots.data.TagsRemoteDataSource
import com.glebgol.photospots.domain.TagData
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
}
