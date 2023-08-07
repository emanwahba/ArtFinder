package com.ah.artfinder.presentation.artlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ah.artfinder.domain.model.Art
import com.ah.artfinder.presentation.artlist.components.ArtListItem
import com.ah.artfinder.presentation.artlist.components.SearchBar

@Composable
fun ArtListScreen(
    viewModel: ArtListViewModel = hiltViewModel(),
    navigateToArtDetailsScreen: (String) -> Unit
) {
    val state = viewModel.state.value

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(
                text = viewModel.searchTerm.value,
                hint = "Search art",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                viewModel.searchArt(it)
            }
            ArtGrid(
                artMapGroupedByArtist = state.artMap,
                navigateToArtDetailsScreen = navigateToArtDetailsScreen
            )
        }

        Box {
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .scale(1.5f)
                )
            }
        }
    }
}


@Composable
fun ArtGrid(
    artMapGroupedByArtist: Map<String, List<Art>>,
    navigateToArtDetailsScreen: (String) -> Unit,
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        artMapGroupedByArtist.forEach { (artist, artList) ->

            item(span = { GridItemSpan(maxLineSpan) })
            {
                Text(
                    text = artist,
                    maxLines = 1,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Left,
                )
            }

            items(artList.size) { index ->
                ArtListItem(
                    art = artList[index],
                    onItemClick = {
                        navigateToArtDetailsScreen(artList[index].objectNumber)
                    }
                )
            }
        }
    }
}