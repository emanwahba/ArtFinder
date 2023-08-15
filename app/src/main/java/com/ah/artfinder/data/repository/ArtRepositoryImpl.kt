package com.ah.artfinder.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ah.artfinder.data.paging.ArtPagingSource
import com.ah.artfinder.data.remote.ArtApi
import com.ah.artfinder.data.remote.entity.ArtDetailsApiResponse
import com.ah.artfinder.data.remote.entity.ArtEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val api: ArtApi
) : ArtRepository {
    override fun searchArt(
        searchQuery: String?,
        sortedBy: String?,
        pageSize: Int,
    ): Flow<PagingData<ArtEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ArtPagingSource(
                    api = api,
                    searchQuery = searchQuery,
                    sortedBy = sortedBy,
                    pageSize = pageSize
                )
            }
        ).flow
    }

    override suspend fun getArtDetails(id: String): ArtDetailsApiResponse {
        return api.getArtDetails(id)
    }
}