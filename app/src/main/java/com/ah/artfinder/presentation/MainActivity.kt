package com.ah.artfinder.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ah.artfinder.presentation.navigation.AppNavigation
import com.ah.artfinder.presentation.ui.theme.ArtFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtFinderTheme {
                AppNavigation()
            }
        }
    }
}