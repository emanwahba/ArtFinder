package com.ah.artfinder.presentation.artlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.ah.artfinder.domain.model.Art
import com.ah.artfinder.domain.usecase.SearchArtSortedByArtist
import com.ah.artfinder.presentation.artlist.model.ArtListUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ArtListViewModel @Inject constructor(
    private val searchArtUseCase: SearchArtSortedByArtist,
) : ViewModel() {

    private val _query = mutableStateOf("")
    val query: State<String> = _query

    lateinit var pagingData: Flow<PagingData<ArtListUiModel>>

    init {
        searchArt()
    }

    fun searchArt(text: String? = null) {

        text?.let { _query.value = it }

        pagingData = searchArtUseCase(searchQuery = text, pageSize = 20)
            .map { pagingData: PagingData<Art> ->
                pagingData.map { art ->
                    ArtListUiModel.ArtModel(art)
                }.insertSeparators { before, after ->
                    if (before == null && after != null) ArtListUiModel.SeparatorModel(after.art.artist)
                    else if (before != null && after != null && before.art.artist != after.art.artist) {
                        ArtListUiModel.SeparatorModel(after.art.artist)
                    } else
                        null
                }
            }
            .cachedIn(viewModelScope)
    }
}