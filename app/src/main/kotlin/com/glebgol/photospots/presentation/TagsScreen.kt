package com.glebgol.photospots.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glebgol.photospots.data.TagDto
import com.glebgol.photospots.data.mapToData

@Composable
fun TagsScreen(
    modifier: Modifier = Modifier,
    viewModel: TagsViewModel = hiltViewModel<TagsViewModel>()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(TagsIntent.LoadTagsIntent)
    }

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
                .padding(10.dp)
        ) {

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
                    image = "test.com",
                    description = "Hello world",
                    longitude = 14.5,
                    latitude = 35.8
                ),
                TagDto(
                    image = "test.com",
                    description = "Hello guys",
                    longitude = 142.5,
                    latitude = -35.8
                )).mapToData()
        ),
        modifier = Modifier,
        onTagClick = {}
    )
}
