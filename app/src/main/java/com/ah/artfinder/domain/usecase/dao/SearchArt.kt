package com.ah.artfinder.domain.usecase.dao

import android.util.Log
import com.ah.artfinder.data.repository.ArtRepository
import com.ah.artfinder.domain.mapper.toArtObject
import com.ah.artfinder.domain.model.ArtObject
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
    ): Flow<NetworkResult<List<ArtObject>>> = flow {
        try {
            emit(NetworkResult.Loading())

            val apiResponse = repository.searchArt(
                searchQuery,
                sortedBy,
                currentPage,
                pageSize
            ).artObjectsEntity.map { it.toArtObject() }

            emit(NetworkResult.Success(apiResponse))

        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            e.message?.let { Log.d("NetworkResult", it) }
            emit(NetworkResult.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}