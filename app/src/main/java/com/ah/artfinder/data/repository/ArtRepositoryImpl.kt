package com.ah.artfinder.data.repository

import com.ah.artfinder.data.remote.ArtApi
import com.ah.artfinder.data.remote.entity.ArtApiResponse
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val api: ArtApi
) : ArtRepository {
    override suspend fun searchArt(
        searchQuery: String?,
        sortedBy: String?,
        currentPage: Int?,
        pageSize: Int?
    ): ArtApiResponse {
        return api.searchArt(searchQuery, sortedBy, currentPage, pageSize)
    }
}