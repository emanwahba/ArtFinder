package com.ah.artfinder.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun boldText(text: String) = listOf(
    AnnotatedString.Range(
        SpanStyle(fontWeight = FontWeight.Bold),
        start = 0,
        end = text.length
    )
)