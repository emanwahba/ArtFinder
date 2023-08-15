package com.ah.artfinder.presentation.artdetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ah.artfinder.R
import com.ah.artfinder.presentation.common.ErrorUi
import com.ah.artfinder.presentation.common.LoadingUi
import com.ah.artfinder.presentation.common.boldText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtDetailsScreen(
    viewModel: ArtDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val state = viewModel.state.value

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        state.artDetails?.let { artDetails ->
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
            ) {

                stickyHeader {
                    TopSection(artDetails.title, navigateBack)
                }

                item {
                    DetailsImage(
                        title = artDetails.title, imageUrl = artDetails.imageUrl
                    )
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .padding(all = dimensionResource(R.dimen.details_column_padding)),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        artDetails.artist?.let {
                            Text(
                                text = AnnotatedString(
                                    text = stringResource(id = R.string.artist) + artDetails.artist,
                                    spanStyles = boldText(stringResource(id = R.string.artist))
                                )
                            )
                        }
                        artDetails.materials?.let {
                            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.details_spacer_padding)))
                            Text(
                                text = AnnotatedString(
                                    text = stringResource(id = R.string.material) + artDetails.materials,
                                    spanStyles = boldText(stringResource(id = R.string.material))
                                )
                            )
                        }
                        artDetails.location?.let {
                            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.details_spacer_padding)))
                            Text(
                                text = AnnotatedString(
                                    text = stringResource(id = R.string.location) + artDetails.location,
                                    spanStyles = boldText(stringResource(id = R.string.location))
                                )
                            )
                        }
                        artDetails.description?.let {
                            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.details_spacer_padding)))
                            Text(
                                text = AnnotatedString(
                                    text = stringResource(id = R.string.description) + artDetails.description,
                                    spanStyles = boldText(stringResource(id = R.string.description))
                                )
                            )
                        }
                    }

                }
            }
        }

        if (state.isLoading) {
            LoadingUi(scale = 1.2f)
        }
        if (!state.error.isNullOrEmpty()) {
            ErrorUi(
                message = state.error,
                refresh = { viewModel.getDetails(viewModel.artId.value) }
            )
        }
    }
}

@Composable
fun TopSection(
    title: String,
    navigateBack: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = dimensionResource(R.dimen.details_top_section_padding)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.details_back_arrow_size))
                    .clickable {
                        navigateBack()
                    })
            DetailsTitle(
                title = title
            )
        }
    }
}

@Composable
fun DetailsTitle(
    title: String, modifier: Modifier = Modifier
) {
    Text(
        text = title,
        fontSize = with(LocalDensity.current) {
            dimensionResource(R.dimen.details_title_font_size).toSp()
        },
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.details_title_horizontal_padding))
    )
}

@Composable
fun DetailsImage(
    title: String, imageUrl: String?, modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        SubcomposeAsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .fallback(R.drawable.image_not_available)
            .build(),
            contentDescription = title,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth(),
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.scale(0.3f)
                )
            })
    }
}