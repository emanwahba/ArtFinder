package com.ah.artfinder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ah.artfinder.presentation.Screen
import com.ah.artfinder.presentation.ScreenParameter
import com.ah.artfinder.presentation.artdetails.ArtDetailsScreen
import com.ah.artfinder.presentation.artlist.ArtListScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ArtListScreen.route
    ) {
        composable(
            route = Screen.ArtListScreen.route
        ) {
            ArtListScreen(
                navigateToArtDetailsScreen = { id ->
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