package com.ah.artfinder.domain.usecase.dao

import androidx.paging.PagingData
import androidx.paging.map
import com.ah.artfinder.data.repository.ArtRepository
import com.ah.artfinder.domain.mapper.toArt
import com.ah.artfinder.domain.model.Art
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtDao @Inject constructor(
    private val repository: ArtRepository
) {
    fun search(
        searchQuery: String? = null,
        sortedBy: String? = null,
        pageSize: Int = API_PAGE_SIZE
    ): Flow<PagingData<Art>> {
        return repository.searchArt(
            searchQuery,
            sortedBy,
            pageSize
        ).map { pagingData -> pagingData.map { artEntity -> artEntity.toArt() } }
    }

    companion object {
        const val API_PAGE_SIZE = 10
    }
}