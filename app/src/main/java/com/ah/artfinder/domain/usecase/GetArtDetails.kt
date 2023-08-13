package com.ah.artfinder.domain.usecase

import android.util.Log
import com.ah.artfinder.data.repository.ArtRepository
import com.ah.artfinder.domain.mapper.toArtDetails
import com.ah.artfinder.domain.model.ArtDetails
import com.ah.artfinder.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetArtDetails @Inject constructor(
    private val repository: ArtRepository
) {
    operator fun invoke(
        id: String
    ): Flow<NetworkResult<ArtDetails>> = flow {
        try {
            emit(NetworkResult.Loading())

            val artDetails = repository.getArtDetails(id).artDetailsEntity.toArtDetails()

            emit(NetworkResult.Success(artDetails))

        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            e.message?.let { Log.d("NetworkResult", it) }
            emit(NetworkResult.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}