package com.glebgol.photospots.data.retrofit

import com.glebgol.photospots.data.TagApi
import com.glebgol.photospots.data.dto.TagDetailsDto
import com.glebgol.photospots.data.dto.TagDto
import com.glebgol.photospots.data.TagsRemoteDataSource
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
}
