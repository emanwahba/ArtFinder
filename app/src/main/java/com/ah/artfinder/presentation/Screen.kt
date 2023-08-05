package com.ah.artfinder.presentation

sealed class Screen(val route: String) {
    data object ArtListScreen: Screen("art_list_screen")
    data object ArtDetailsScreen: Screen("art_detail_screen")
}
