package com.ah.artfinder.domain.usecase

import com.ah.artfinder.data.SortedBy
import com.ah.artfinder.domain.model.Art
import com.ah.artfinder.domain.usecase.dao.SearchArt
import com.ah.artfinder.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchArtSortedByArtist @Inject constructor(
    private val searchArt: SearchArt
) {
    operator fun invoke(
        searchQuery: String? = null,
        currentPage: Int? = null,
        pageSize: Int? = null
    ): Flow<NetworkResult<List<Art>>> {
        return searchArt.search(searchQuery, SortedBy.ARTIST.value, currentPage, pageSize)
    }
}