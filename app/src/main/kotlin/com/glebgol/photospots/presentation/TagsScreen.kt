package com.glebgol.photospots.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glebgol.photospots.data.TagDto
import com.glebgol.photospots.data.TagRepositoryImpl
import com.glebgol.photospots.data.mapToData
import com.glebgol.photospots.domain.GetTagsUseCase

@Composable
fun TagsScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = remember { TagsViewModel(GetTagsUseCase(TagRepositoryImpl())) }
    viewModel.onIntent(TagsIntent.LoadTagsIntent)
    val state by viewModel.state.collectAsState()

    TagsScreenContent(
        modifier = modifier,
        state = state,
        onTagClick = {}
    )
}

@Composable
fun TagsScreenContent(
    modifier: Modifier = Modifier,
    state: TagsState,
    onTagClick: (String) -> Unit
) {
    if (state.tags != null) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(30.dp)
                .background(Color.Green)) {

            items(state.tags) { tag ->
                TagRow(
                    tag = tag
                )
            }
        }
    }
}

@Preview
@Composable
private fun TagsScreenPreview() {
    TagsScreenContent(
        state = TagsState(
            tags = listOf(
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
        ),
        modifier = Modifier,
        onTagClick = {}
    )
}
