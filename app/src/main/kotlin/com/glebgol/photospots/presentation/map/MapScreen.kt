package com.glebgol.photospots.presentation.map

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.glebgol.photospots.domain.data.TagData
import com.glebgol.photospots.presentation.tagdetails.TagDetailsScreen
import com.glebgol.photospots.presentation.tagdetails.TagDetailsViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel<MapViewModel>(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedTag by remember { mutableStateOf<TagData?>(null) }
    viewModel.loadMarkers()

    if (showBottomSheet && selectedTag != null) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            TagDetailsScreen(
                id = selectedTag!!.id,
                viewModel = hiltViewModel<TagDetailsViewModel>(),
                onBackClick = { showBottomSheet = false }
            )
        }
    }

    MapScreenContent(
        modifier = modifier,
        state = state,
        onMarkerClick = { tag ->
            selectedTag = tag
            showBottomSheet = true
        }
    )
}

@Composable
private fun MapScreenContent(
    modifier: Modifier = Modifier,
    state: MapState,
    initialPosition: GeoPoint = GeoPoint(53.76, 27.60),
    initialZoom: Double = 5.0,
    minZoom: Double = 3.5,
    maxZoom: Double = 19.0,
    onMarkerClick: (TagData) -> Unit = {}
) {
    if (state.isLoading) {
        Text(text = "Loading")
    } else {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                MapView(context).apply {
                    setTileSource(TileSourceFactory.WIKIMEDIA)
                    minZoomLevel = minZoom
                    maxZoomLevel = maxZoom
                    setMultiTouchControls(true)

                    controller.setZoom(initialZoom)
                    controller.setCenter(initialPosition)

                    addMarkers(state, onMarkerClick)
                }
            },
            update = { mapView ->
                mapView.overlays.clear()
                mapView.addMarkers(state, onMarkerClick)
            }
        )
    }
}

private fun MapView.addMarkers(
    state: MapState,
    onMarkerClick: (TagData) -> Unit
) {
    state.markers.forEach { marker ->
        val markerView = Marker(this).apply {
            position = GeoPoint(marker.latitude, marker.longitude)
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            title = marker.description
            snippet = marker.description
            setOnMarkerClickListener { _, _ ->
                onMarkerClick(marker)
                true
            }
        }
        overlays.add(markerView)
    }
}
