package com.ah.artfinder.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ah.artfinder.domain.model.ArtObject
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
                        ArtListScreen(navigateToArtDetailsScreen = { artObject ->
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                ScreenParameter.ArtObjectKey.key,
                                artObject
                            )
                            navController.navigate(Screen.ArtDetailsScreen.route)
                        })
                    }

                    composable(
                        route = Screen.ArtDetailsScreen.route
                    ) {
                        val artObject = remember {
                            navController.previousBackStackEntry?.savedStateHandle?.get<ArtObject>(
                                ScreenParameter.ArtObjectKey.key
                            ) ?: ArtObject("", "", "", "", "", "")
                        }
                        ArtDetailsScreen(
                            artObject = artObject,
                            navigateBack = {
                                navController.navigateUp()
                            },
                        )
                    }
                }
            }
        }
    }
}