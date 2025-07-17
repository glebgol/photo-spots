package com.glebgol.photospots.presentation.tagdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.glebgol.photospots.domain.TagDetailsData

@Composable
fun TagDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: TagDetailsViewModel,
    id: String,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.onIntent(TagDetailsIntent.LoadTagDetailsIntent(id))
    }

    TagDetailsContent(
        modifier = modifier,
        state = state,
        onLikeClick = {
            viewModel.onIntent(
                TagDetailsIntent.ToggleLikeIntent(state.tagDetails!!.id)
            )
        },
        onBackClick = onBackClick
    )
}

@Composable
private fun TagDetailsContent(
    modifier: Modifier = Modifier,
    state: TagDetailsState,
    onLikeClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement
            .spacedBy(20.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onBackClick, modifier = Modifier.size(100.dp)) {
            Text(text = "Back")
        }
        val tag = state.tagDetails
        if (tag != null) {
            AsyncImage(
                model = tag.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .heightIn(min = 400.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Text(text = tag.description)
            Text(text = "Coordinates ${tag.latitude} ${tag.longitude}")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TagDetailsContentPreview() {
    TagDetailsContent(
        state = TagDetailsState(
            tagDetails = TagDetailsData(
                id = "",
                imageUrl = "",
                description = "Description",
                longitude = 10.0,
                latitude = -10.0,
            ),
            isLoading = false,
            isError = false
        ),
        onLikeClick = {},
        onBackClick = { }
    )
}
