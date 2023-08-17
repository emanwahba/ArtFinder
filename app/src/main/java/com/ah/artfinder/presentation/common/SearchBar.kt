package com.ah.artfinder.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import com.ah.artfinder.R

@Composable
fun SearchBar(
    modifier: Modifier = Modifier.testTag("search_bar_test_tag"),
    text: String = "",
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    var showClearButton by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(dimensionResource(R.dimen.search_bar_shadow), CircleShape)
                .background(Color.White, CircleShape)
                .padding(
                    horizontal = dimensionResource(R.dimen.search_bar_horizontal_padding),
                    vertical = dimensionResource(R.dimen.search_bar_vertical_padding)
                )
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused && text.isEmpty()
                    showClearButton = (it.isFocused)
                },

            )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.Gray,
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.search_bar_horizontal_padding),
                        vertical = dimensionResource(R.dimen.search_bar_vertical_padding)
                    )
            )
        }
    }
}