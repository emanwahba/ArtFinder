package com.ah.artfinder.domain.usecase.dao

import android.util.Log
import com.ah.artfinder.data.repository.ArtRepository
import com.ah.artfinder.domain.mapper.toArt
import com.ah.artfinder.domain.model.Art
import com.ah.artfinder.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchArt @Inject constructor(
    private val repository: ArtRepository
) {
    fun search(
        searchQuery: String? = null,
        sortedBy: String? = null,
        currentPage: Int? = null,
        pageSize: Int? = null,
    ): Flow<NetworkResult<List<Art>>> = flow {
        try {
            emit(NetworkResult.Loading())

            val artList = repository.searchArt(
                searchQuery,
                sortedBy,
                currentPage,
                pageSize
            ).artListEntity.map { it.toArt() }

            emit(NetworkResult.Success(artList))

        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            e.message?.let { Log.d("NetworkResult", it) }
            emit(NetworkResult.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}