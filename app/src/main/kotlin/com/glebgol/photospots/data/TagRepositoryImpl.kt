package com.glebgol.photospots.data

import com.glebgol.photospots.domain.TagData
import com.glebgol.photospots.domain.TagRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor() : TagRepository {
    override suspend fun getAllTags(): List<TagData> {
        delay(2000)
        return listOf(
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            ),
            TagDto(
                image = "http://localhost:8080/images/60b705a4-e048-4bb9-b9c0-01c1693b905d",
                description = "Hello guys",
                longitude = 14.5,
                latitude = 35.8
            )
        ).mapToData()
    }
}
