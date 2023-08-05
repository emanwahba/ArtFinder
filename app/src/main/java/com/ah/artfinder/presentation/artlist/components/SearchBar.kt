package com.ah.artfinder.presentation.artlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
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

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                onSearch(it)
            },
//            trailingIcon = {
//                AnimatedVisibility(
//                    visible = showClearButton,
//                    enter = fadeIn(),
//                    exit = fadeOut()
//                ) {
//                    IconButton(onClick = {
////                        onClearClick()
//                    }) {
//                        Icon(
//                            imageVector = Icons.Filled.Close,
//                            contentDescription = null)
//                    }
//
//                }
//            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
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
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }

//        if (text.isNotEmpty()) {
//            IconButton(onClick = { onSearch("") }) {
//                Icon(imageVector = Icons.Filled.Clear,
//                    contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 20.dp, vertical = 12.dp)
//                    .align(Alignment.End))
//            }
//        }


    }
}