package com.ah.artfinder.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.ah.artfinder.R

@Composable
fun ErrorUi(
    modifier: Modifier = Modifier,
    message: String,
    refresh: (() -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = message,
            fontSize = with(LocalDensity.current) {
                dimensionResource(R.dimen.error_ui_font_size).toSp()
            },
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.error_ui_text_horizontal_padding))
        )

        refresh?.let {
            Spacer(Modifier.height(dimensionResource(R.dimen.error_ui_spacer_height)))
            Button(
                onClick = refresh,
                shape = CutCornerShape(dimensionResource(R.dimen.error_ui_button_corner)),
            ) {
                Text(
                    text = stringResource(R.string.retry),
                    fontSize = with(LocalDensity.current) {
                        dimensionResource(R.dimen.error_ui_font_size).toSp()
                    },
                    color = MaterialTheme.colorScheme.surface,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}