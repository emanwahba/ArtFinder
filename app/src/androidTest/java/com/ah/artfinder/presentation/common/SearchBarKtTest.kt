package com.ah.artfinder.presentation.common

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.ah.artfinder.presentation.ui.theme.ArtFinderTheme
import org.junit.Rule
import org.junit.Test

class SearchBarKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun search_bar_should_be_shown() {
        composeTestRule.setContent {
            ArtFinderTheme {
                SearchBar()
            }
        }

        composeTestRule.onNodeWithTag("search_bar_test_tag").assertIsDisplayed()
    }
}