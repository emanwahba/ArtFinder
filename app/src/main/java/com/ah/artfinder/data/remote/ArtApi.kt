package com.ah.artfinder.data.remote

import com.ah.artfinder.data.remote.entity.ArtApiResponse
import com.ah.artfinder.data.remote.entity.ArtDetailsApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//TODO not production, to be moved to gradle.properties
private const val API_KEY = "0fiuZFh4"

interface ArtApi {

    @GET("api/en/collection?key=$API_KEY")
    suspend fun searchArt(
        @Query("q") searchQuery: String?,
        @Query("s") sortedBy: String?,
        @Query("p") pageNumber: Int?, // [0-n] default 0
        @Query("ps") pageSize: Int?, // The number of results per page [1-100] default 10
    ): ArtApiResponse

    @GET("api/en/collection/{id}?key=$API_KEY")
    suspend fun getArtDetails(
        @Path("id") id: String
    ): ArtDetailsApiResponse

    companion object {
        const val FIRST_PAGE_INDEX = 0
    }
}