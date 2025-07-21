package com.glebgol.photospots.presentation.tagdetails

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HeartButton(
    isFavourite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    animationDuration: Int = 300
) {
    AnimatedContent(
        targetState = isFavourite,
        transitionSpec = {
            fadeIn(animationSpec = tween(animationDuration)) togetherWith
                    fadeOut(animationSpec = tween(animationDuration))
        },
        label = "HeartAnimation"
    ) { targetFavourite ->
        Icon(
            imageVector = if (targetFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = if (targetFavourite) "Remove from favorites" else "Add to favorites",
            tint = if (targetFavourite) Color.Red else Color.Gray,
            modifier = modifier
                .size(size)
                .clickable(onClick = onClick)
        )
    }
}
