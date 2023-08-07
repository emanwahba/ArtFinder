package com.ah.artfinder.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ah.artfinder.presentation.artdetails.ArtDetailsScreen
import com.ah.artfinder.presentation.artlist.ArtListScreen
import com.ah.artfinder.presentation.ui.theme.ArtFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtFinderTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.ArtListScreen.route
                ) {
                    composable(
                        route = Screen.ArtListScreen.route
                    ) {
                        ArtListScreen(navigateToArtDetailsScreen = { id ->
                            navController.navigate(Screen.ArtDetailsScreen.route + "/$id")
                        })
                    }

                    composable(
                        route = Screen.ArtDetailsScreen.route + "/{${ScreenParameter.ArtId.key}}",
                        arguments = listOf(navArgument(ScreenParameter.ArtId.key) {
                            type = NavType.StringType
                        })
                    ) {
                        ArtDetailsScreen(
                            navigateBack = {
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}