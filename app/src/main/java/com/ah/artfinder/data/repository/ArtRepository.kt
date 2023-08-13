package com.ah.artfinder.data.repository

import androidx.paging.PagingData
import com.ah.artfinder.data.remote.entity.ArtDetailsApiResponse
import com.ah.artfinder.data.remote.entity.ArtEntity
import kotlinx.coroutines.flow.Flow

interface ArtRepository {
    fun searchArt(
        searchQuery: String? = null,
        sortedBy: String? = null,
        pageSize: Int
    ): Flow<PagingData<ArtEntity>>

    suspend fun getArtDetails(
        id: String
    ): ArtDetailsApiResponse
}