package com.ah.artfinder.data.remote

import com.ah.artfinder.data.remote.entity.ArtApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

//TODO not production, to be moved to gradle.properties
private const val API_KEY = "0fiuZFh4"

interface ArtApi {

    // https://www.rijksmuseum.nl/api/nl/collection?key=[api-key]
    @GET("api/nl/collection?key=$API_KEY")
    suspend fun searchArt(
        @Query("q") searchQuery: String?,
        @Query("s") sortedBy: String?,
        @Query("p") currentPage: Int?, // Page number [0-n] default 0
        @Query("ps") pageSize: Int?, // The number of results per page [1-100] default 10
    ): ArtApiResponse
}