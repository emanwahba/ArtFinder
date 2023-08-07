package com.ah.artfinder.data.repository

import com.ah.artfinder.data.remote.entity.ArtApiResponse
import com.ah.artfinder.data.remote.entity.ArtDetailsApiResponse

interface ArtRepository {
    suspend fun searchArt(
        searchQuery: String? = null,
        sortedBy: String? = null,
        currentPage: Int? = null,
        pageSize: Int? = null
    ): ArtApiResponse

    suspend fun getArtDetails(
        id: String
    ): ArtDetailsApiResponse
}