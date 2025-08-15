package com.glebgol.photospots.app

import android.Manifest
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.glebgol.photospots.R
import com.glebgol.photospots.presentation.createtag.CreateTagScreen
import com.glebgol.photospots.presentation.map.MapScreen
import com.glebgol.photospots.presentation.tagdetails.TagDetailsScreen
import com.glebgol.photospots.presentation.tagdetails.TagDetailsViewModel
import com.glebgol.photospots.presentation.taglist.TagsScreen
import com.glebgol.photospots.presentation.taglist.TagsViewModel
import com.glebgol.photospots.presentation.theme.PhotospotsTheme
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            1
        )
        loadOsmdroid()
        enableEdgeToEdge()
        setContent {
            PhotospotsTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val isMainScreen = shouldShowBottomBar(navBackStackEntry)

                val cameraPermissionLauncher = requestCameraLauncher {
                    navController.navigate(Route.Create)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (isMainScreen) {
                            BottomNavigationBar(navController)
                        }
                    },
                    floatingActionButton = {
                        if (isMainScreen) {
                            FloatingActionButton(onClick = {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                            }
                        }
                    }) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Route.Graph,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        navigation<Route.Graph>(
                            startDestination = Route.TagsListRoute
                        ) {
                            composable<Route.TagsListRoute> {
                                TagsScreen(
                                    viewModel = hiltViewModel<TagsViewModel>(),
                                    modifier = Modifier,
                                    onTagClick = {
                                        navController.navigate(Route.TagDetailsRoute(it))
                                    }
                                )
                            }

                            composable<Route.TagDetailsRoute> { entry ->
                                val args = entry.toRoute<Route.TagDetailsRoute>()
                                TagDetailsScreen(
                                    modifier = Modifier,
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

                            composable<Route.Map> { entry ->
                                MapScreen(
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun shouldShowBottomBar(navBackStackEntry: NavBackStackEntry?) =
        navBackStackEntry?.destination?.route in setOf(
            Route.TagsListRoute::class.qualifiedName,
            Route.Map::class.qualifiedName
        )

    private fun loadOsmdroid() {
        Configuration.getInstance().load(
            applicationContext,
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )
    }

    @Composable
    private fun requestCameraLauncher(onSuccess: () -> Unit) =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                onSuccess()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    R.string.permission_required,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}
