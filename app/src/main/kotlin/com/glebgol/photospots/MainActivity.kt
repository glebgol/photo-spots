package com.glebgol.photospots

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.glebgol.photospots.presentation.TagsScreen
import com.glebgol.photospots.presentation.TagsViewModel
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
                                val viewModel = hiltViewModel<TagsViewModel>()
                                TagsScreen(
                                    viewModel = viewModel,
                                    modifier = Modifier.padding(innerPadding),
                                    onTagClick = { navController.navigate(Route.TagDetailsRoute(it)) }
                                )
                            }
                            composable<Route.TagDetailsRoute> {
                                val args = it.toRoute<Route.TagDetailsRoute>()
                                val viewModel = hiltViewModel<TagDetailsViewModel>()
                                TagDetailsScreen(
                                    id = args.id,
                                    viewModel = viewModel,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        }

                    }
                }
            }
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
}
