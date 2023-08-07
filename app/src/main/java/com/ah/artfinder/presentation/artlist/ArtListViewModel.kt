package com.ah.artfinder.presentation.artlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ah.artfinder.domain.usecase.SearchArtSortedByArtist
import com.ah.artfinder.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ArtListViewModel @Inject constructor(
    private val searchArtUseCase: SearchArtSortedByArtist,
) : ViewModel() {

    private val _state = mutableStateOf(ArtListState())
    val state: State<ArtListState> = _state

    private val _searchTerm = mutableStateOf("")
    val searchTerm: State<String> = _searchTerm

    init {
        searchArt()
    }

    fun searchArt(text: String? = null) {

        text?.let { _searchTerm.value = it }

        searchArtUseCase(searchQuery = text, pageSize = 20).onEach { result ->
            when (result) {
                is NetworkResult.Success -> {
                    val artMapGroupedByArtist = result.data?.groupBy { it.artist }
                    _state.value = ArtListState(artMap = artMapGroupedByArtist ?: emptyMap())
                }

                is NetworkResult.Error -> {
                    _state.value = ArtListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }

                is NetworkResult.Loading -> {
                    _state.value = ArtListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}