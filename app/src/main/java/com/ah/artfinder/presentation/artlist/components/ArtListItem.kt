package com.ah.artfinder.presentation.artlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ah.artfinder.R
import com.ah.artfinder.domain.model.Art

@Composable
fun ArtListItem(
    art: Art,
    onItemClick: (Art) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable { onItemClick(art) }
            .shadow(
                elevation = dimensionResource(R.dimen.grid_item_elevation),
                shape = RoundedCornerShape(dimensionResource(R.dimen.grid_item_rounded_corner))
            )
            .clip(RoundedCornerShape(dimensionResource(R.dimen.grid_item_rounded_corner)))
            .aspectRatio(1f)
            .background(
                MaterialTheme.colorScheme.surface
            )
    ) {
        Column {
            ListItemImage(
                title = art.longTitle,
                imageUrl = art.imageUrl,
                modifier = Modifier.weight(0.75f)
            )
            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.grid_item_spacer_padding)))
            ListItemTitle(
                title = art.title,
                modifier = Modifier.weight(0.25f)
            )
        }
    }
}

@Composable
fun ListItemImage(
    title: String,
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .fallback(R.drawable.image_not_available)
                .build(),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            filterQuality = FilterQuality.None,
            modifier = Modifier.fillMaxWidth(),
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.scale(0.3f)
                )
            })
    }
}

@Composable
fun ListItemTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        maxLines = 1,
        fontSize = with(LocalDensity.current) {
            dimensionResource(R.dimen.grid_item_title_font_size).toSp()
        },
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.grid_item_title_horizontal_padding))
    )
}