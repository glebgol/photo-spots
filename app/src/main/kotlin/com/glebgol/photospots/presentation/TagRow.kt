package com.glebgol.photospots.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.glebgol.photospots.domain.TagData

@Composable
fun TagRow(
    modifier: Modifier = Modifier,
    tag: TagData
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = tag.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(5.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
        )
        Text(text = tag.description)
        Text(text = "${tag.latitude} ${tag.longitude}")
    }
}

@Preview(showBackground = true)
@Composable
private fun TagRowPreview() {
    TagRow(
        tag = TagData(
            imageUrl = "",
            description = "Minsk",
            latitude = 10.0,
            longitude = 12.0
        )
    )
}
