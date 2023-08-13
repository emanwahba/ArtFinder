package com.ah.artfinder.domain.usecase

import androidx.paging.PagingData
import com.ah.artfinder.data.SortedBy
import com.ah.artfinder.domain.model.Art
import com.ah.artfinder.domain.usecase.dao.SearchArt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchArtSortedByArtist @Inject constructor(
    private val searchArt: SearchArt
) {
    operator fun invoke(
        searchQuery: String? = null,
        pageSize: Int? = null,
    ): Flow<PagingData<Art>> {
        return if (pageSize != null) {
            searchArt.search(searchQuery, SortedBy.ARTIST.value, pageSize)
        } else {
            searchArt.search(searchQuery, SortedBy.ARTIST.value)
        }
    }
}