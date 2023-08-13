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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.ah.artfinder.presentation.artlist.components.ArtListItem
import com.ah.artfinder.presentation.artlist.model.ArtListUiModel
import com.ah.artfinder.presentation.common.ErrorUi
import com.ah.artfinder.presentation.common.LoadingUi
import com.ah.artfinder.presentation.common.SearchBar

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
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(
                text = viewModel.query.value,
                hint = "Search art",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
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
            LoadingUi()
        }

        is LoadState.Error -> {
            val message =
                (pagingData.loadState.append as? LoadState.Error)?.error?.message
                    ?: return

            ErrorUi(
                message = message,
                refresh = { pagingData.retry() }
            )
        }

        else -> {
            ArtGrid(pagingData, navigateToArtDetailsScreen)
        }
    }
}

@Composable
private fun ArtGrid(
    pagingData: LazyPagingItems<ArtListUiModel>,
    navigateToArtDetailsScreen: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 24.dp,
            end = 24.dp,
            top = 8.dp,
            bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        items(
            count = pagingData.itemCount,
            key = pagingData.itemKey(),
            span = { index ->
                val span = if (pagingData.peek(index) is ArtListUiModel.SeparatorModel) {
                    maxLineSpan
                } else {
                    1
                }
                GridItemSpan(span)
            }
        ) { index ->
            pagingData[index]?.let { uiModel ->
                when (uiModel) {
                    is ArtListUiModel.ArtModel -> {
                        ArtListItem(
                            art = uiModel.art,
                            onItemClick = {
                                navigateToArtDetailsScreen(uiModel.art.objectNumber)
                            })
                    }

                    is ArtListUiModel.SeparatorModel -> {
                        Text(
                            text = uiModel.description,
                            maxLines = 1,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Left,
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
                    LoadingUi()
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
                        refresh = { pagingData.retry() })
                }
            }

            else -> {}
        }
    }
}