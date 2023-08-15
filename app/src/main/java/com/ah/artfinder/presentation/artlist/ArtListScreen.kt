package com.ah.artfinder.presentation.artlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.ah.artfinder.R
import com.ah.artfinder.presentation.artlist.components.ArtListItem
import com.ah.artfinder.presentation.artlist.model.ArtListUiModel
import com.ah.artfinder.presentation.common.ErrorUi
import com.ah.artfinder.presentation.common.LoadingUi
import com.ah.artfinder.presentation.common.SearchBar

const val GRID_ITEM_SPAN = 1

@Composable
fun ArtListScreen(
    viewModel: ArtListViewModel = hiltViewModel(),
    navigateToArtDetailsScreen: (String) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.list_screen_spacer_height)))
            SearchBar(
                text = viewModel.query.value,
                hint = stringResource(id = R.string.searchArt),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.search_bar_padding))
            ) {
                viewModel.searchArt(it)
            }

            ArtPagingData(
                navigateToArtDetailsScreen = navigateToArtDetailsScreen
            )
        }
    }
}

@Composable
fun ArtPagingData(
    viewModel: ArtListViewModel = hiltViewModel(),
    navigateToArtDetailsScreen: (String) -> Unit,
) {
    val pagingData = viewModel.pagingData.collectAsLazyPagingItems()

    when (pagingData.loadState.refresh) {
        is LoadState.Loading -> {
            LoadingUi(scale = 1.2f)
        }

        is LoadState.Error -> {
            val message =
                (pagingData.loadState.append as? LoadState.Error)?.error?.message
                    ?: stringResource(R.string.no_internet_connection)

            ErrorUi(
                message = message,
                refresh = { pagingData.retry() }
            )
        }

        else -> {
            if (pagingData.itemCount == 0) {
                ErrorUi(
                    message = stringResource(R.string.no_data_found)
                )
            } else {
                ArtGrid(pagingData, navigateToArtDetailsScreen)
            }
        }
    }
}

@Composable
private fun ArtGrid(
    pagingData: LazyPagingItems<ArtListUiModel>,
    navigateToArtDetailsScreen: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = dimensionResource(R.dimen.grid_cells_minimum_size)),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = dimensionResource(R.dimen.grid_content_padding),
            end = dimensionResource(R.dimen.grid_content_padding),
            top = dimensionResource(R.dimen.grid_content_padding_top),
            bottom = dimensionResource(R.dimen.grid_content_padding)
        ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.grid_vertical_arrangement)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.grid_horizontal_arrangement))
    ) {

        items(
            count = pagingData.itemCount,
            key = pagingData.itemKey(),
            span = { index ->
                val span = if (pagingData.peek(index) is ArtListUiModel.SeparatorModel) {
                    maxLineSpan
                } else {
                    GRID_ITEM_SPAN
                }
                GridItemSpan(span)
            }
        ) { index ->
            pagingData[index]?.let { uiModel ->
                when (uiModel) {
                    is ArtListUiModel.ArtModel -> {
                        // Art object
                        ArtListItem(
                            art = uiModel.art,
                            onItemClick = {
                                navigateToArtDetailsScreen(uiModel.art.objectNumber)
                            })
                    }

                    is ArtListUiModel.SeparatorModel -> {
                        // Artist name
                        GroupHeader(
                            text = uiModel.description,
                            modifier = Modifier.padding(
                                top = dimensionResource(R.dimen.grid_header_padding_top)
                            )
                        )
                    }
                }
            }
        }

        when (pagingData.loadState.append) {
            is LoadState.Loading -> {
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    LoadingUi(
                        modifier = Modifier.padding(dimensionResource(R.dimen.gris_paging_load_more_padding)),
                        scale = 1f
                    )
                }
            }

            is LoadState.Error -> {
                val message =
                    (pagingData.loadState.append as? LoadState.Error)?.error?.message
                        ?: return@LazyVerticalGrid

                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    ErrorUi(
                        message = message,
                        refresh = { pagingData.retry() }
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
fun GroupHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines = 1,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Left
    )
}