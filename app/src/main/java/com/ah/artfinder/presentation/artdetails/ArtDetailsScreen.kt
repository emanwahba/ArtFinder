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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ah.artfinder.R

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
                        title = artDetails.title,
                        imageUrl = artDetails.imageUrl
                    )
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .padding(all = 16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        artDetails.artist?.let {
                            Text(
                                text = AnnotatedString(
                                    text = stringResource(id = R.string.artist) + artDetails.artist,
                                    spanStyles = boldToText(stringResource(id = R.string.artist))
                                )
                            )
                        }
                        artDetails.materials?.let {
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                text = AnnotatedString(
                                    text = stringResource(id = R.string.material) + artDetails.materials,
                                    spanStyles = boldToText(stringResource(id = R.string.material))
                                )
                            )
                        }
                        artDetails.location?.let {
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                text = AnnotatedString(
                                    text = stringResource(id = R.string.location) + artDetails.location,
                                    spanStyles = boldToText(stringResource(id = R.string.location))
                                )
                            )
                        }
                        artDetails.description?.let {
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                text = AnnotatedString(
                                    text = stringResource(id = R.string.description) + artDetails.description,
                                    spanStyles = boldToText(stringResource(id = R.string.description))
                                )
                            )
                        }
                    }

                }
            }
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
                .padding(all = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        navigateBack()
                    }
            )
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
        fontSize = 20.sp,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    )
}

@Composable
fun DetailsImage(
    title: String,
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
    ) {
        SubcomposeAsyncImage(model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
            .fallback(R.drawable.image_not_supported).build(),
            contentDescription = title,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth(),
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.scale(0.3f)
                )
            }
        )
    }
}

@Composable
private fun boldToText(text: String) = listOf(
    AnnotatedString.Range(
        SpanStyle(fontWeight = FontWeight.Bold),
        start = 0,
        end = text.length
    )
)