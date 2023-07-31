package com.ah.artfinder.data.repository

import com.ah.artfinder.data.remote.entity.ArtApiResponse

interface ArtRepository {
    suspend fun searchArt(
        searchQuery: String? = null,
        sortedBy: String? = null,
        currentPage: Int?,
        pageSize: Int?
    ): ArtApiResponse
}