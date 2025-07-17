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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        onTagClick = {},
        onError = { viewModel.onIntent(TagsIntent.LoadTagsIntent) }
    )
}

@Composable
fun TagsScreenContent(
    modifier: Modifier = Modifier,
    state: TagsState,
    onTagClick: (String) -> Unit,
    onError: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            state.isError -> Box(
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
            state.isLoading -> Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    text = "Loading",
                    fontSize = 20.sp
                )
            }
            state.tags != null -> LazyColumn(
                modifier = Modifier
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
}

@Preview(showBackground = true)
@Composable
private fun TagsScreenPreview() {
    TagsScreenContent(
        modifier = Modifier,
        state = TagsState(
            isError = true,
            isLoading = true,
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
                )
            ).mapToData()
        ),
        onTagClick = {},
        onError = {}
    )
}
