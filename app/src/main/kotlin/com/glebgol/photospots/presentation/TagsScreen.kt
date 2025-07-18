package com.glebgol.photospots.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.glebgol.photospots.data.dto.TagDto
import com.glebgol.photospots.data.mapToData
import com.glebgol.photospots.domain.TagData

@Composable
fun TagsScreen(
    modifier: Modifier = Modifier,
    viewModel: TagsViewModel,
    onTagClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(TagsIntent.LoadTagsIntent)
    }

    TagsScreenContent(
        modifier = modifier,
        state = state,
        onTagClick = onTagClick,
        onError = { viewModel.onIntent(TagsIntent.LoadTagsIntent) },
    )
}

@Composable
fun TagsScreenContent(
    modifier: Modifier = Modifier,
    state: TagsState,
    onTagClick: (String) -> Unit,
    onError: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            state.isError -> ErrorState(onError)
            state.isLoading -> LoadingState()
            state.tags != null -> TagsList(state.tags, onTagClick)
        }
    }
}

@Composable
private fun TagsList(
    tags: List<TagData>,
    onTagClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        items(tags) { tag ->
            TagRow(
                tag = tag,
                onTagClick = onTagClick
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = "Loading",
            fontSize = 20.sp
        )
    }
}

@Composable
private fun ErrorState(onError: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onError) {
            Text(
                text = "Try again",
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TagsScreenPreview() {
    TagsScreenContent(
        modifier = Modifier,
        state = TagsState(
            isError = false,
            isLoading = false,
            tags = listOf(
                TagDto(
                    image = "test.com",
                    description = "Hello world",
                    longitude = 14.5,
                    latitude = 35.8,
                    id = "1"
                ),
                TagDto(
                    image = "test.com",
                    description = "Hello guys",
                    longitude = 142.5,
                    latitude = -35.8,
                    id = "2"
                )
            ).mapToData()
        ),
        onTagClick = {},
        onError = {}
    )
}
