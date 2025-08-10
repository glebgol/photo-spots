package com.glebgol.photospots

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.glebgol.photospots.presentation.TagsScreen
import com.glebgol.photospots.presentation.TagsViewModel
import com.glebgol.photospots.presentation.createtag.CreateTagScreen
import com.glebgol.photospots.presentation.tagdetails.TagDetailsScreen
import com.glebgol.photospots.presentation.tagdetails.TagDetailsViewModel
import com.glebgol.photospots.presentation.theme.PhotospotsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotospotsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Route.Graph
                    ) {
                        navigation<Route.Graph>(
                            startDestination = Route.TagsListRoute
                        ) {
                            composable<Route.TagsListRoute> {
                                val cameraPermissionLauncher = requestCameraLauncher {
                                    navController.navigate(Route.Create) }
                                TagsScreen(
                                    viewModel = hiltViewModel<TagsViewModel>(),
                                    modifier = Modifier.padding(innerPadding),
                                    onTagClick = {
                                        navController.navigate(Route.TagDetailsRoute(it))
                                    },
                                    onTagCreateClick = {
                                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                )
                            }

                            composable<Route.TagDetailsRoute> { entry ->
                                val args = entry.toRoute<Route.TagDetailsRoute>()
                                TagDetailsScreen(
                                    id = args.id,
                                    viewModel = hiltViewModel<TagDetailsViewModel>(),
                                    onBackClick = {
                                        navController.navigateUp()
                                    }
                                )
                            }

                            composable<Route.Create> { entry ->
                                CreateTagScreen(
                                    modifier = Modifier,
                                    backClicked = navController::navigateUp,
                                    onSuccess = {
                                        navController.navigate(Route.TagsListRoute) {
                                            popUpTo(Route.TagsListRoute) {
                                                inclusive = true
                                            }
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun requestCameraLauncher(onSuccess: () -> Unit) =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                onSuccess()
            } else {
                Toast.makeText(this@MainActivity,
                    R.string.permission_required,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}

sealed interface Route {
    @Serializable
    data object TagsListRoute: Route

    @Serializable
    data class TagDetailsRoute(val id: String): Route

    @Serializable
    data object Graph

    @Serializable
    data object Create
}
