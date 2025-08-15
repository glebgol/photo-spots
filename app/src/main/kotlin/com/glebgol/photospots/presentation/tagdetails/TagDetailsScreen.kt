package com.glebgol.photospots.presentation.tagdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.glebgol.photospots.domain.data.TagDetailsData

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
                TagDetailsIntent.ToggleLikeIntent(state.tagDetails!!.id, !state.isFavourite)
            )
        },
        onBackClick = onBackClick,
        openOnMap = {
            val tagDetails = state.tagDetails
            if (tagDetails != null) {
                viewModel.onIntent(
                    TagDetailsIntent.OpenTagOnMap(tagDetails.latitude, tagDetails.longitude)
                )
            }
        }
    )
}

@Composable
private fun TagDetailsContent(
    modifier: Modifier = Modifier,
    state: TagDetailsState,
    onLikeClick: () -> Unit,
    onBackClick: () -> Unit,
    openOnMap: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }

        val tag = state.tagDetails
        if (tag != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(
                    20.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = tag.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .heightIn(min = 400.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )

                HeartButton(
                    onClick = onLikeClick,
                    isFavourite = state.isFavourite
                )

                Text(text = tag.description)

                MapButton(
                    onClick = openOnMap,
                    modifier = Modifier.fillMaxWidth()
                )
            }
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
                isFavourite = false,
            ),
            isLoading = false,
            isError = false
        ),
        onLikeClick = {},
        onBackClick = { },
        openOnMap = {}
    )
}
