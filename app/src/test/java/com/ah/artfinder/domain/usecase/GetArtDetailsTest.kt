package com.ah.artfinder.domain.usecase

import com.ah.artfinder.MainDispatcherRule
import com.ah.artfinder.data.remote.entity.ArtDetailsApiResponse
import com.ah.artfinder.data.remote.entity.ArtDetailsEntity
import com.ah.artfinder.data.repository.ArtRepository
import com.ah.artfinder.domain.mapper.toArtDetails
import com.ah.artfinder.domain.model.ArtDetails
import com.ah.artfinder.util.NetworkResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class GetArtDetailsTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var repository: ArtRepository

    private lateinit var getArtDetails: GetArtDetails

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        getArtDetails = GetArtDetails(repository)
    }

    @Test
    fun `when get art details with valid id, verify NetworkResult Loading and Success received with correct data`() =
        runTest {
            val apiResponse = ArtDetailsApiResponse(
                artDetailsEntity = ArtDetailsEntity(
                    "id",
                    "object number",
                    "title",
                    "location",
                    listOf("materials"),
                    "description",
                    null,
                    "artist"
                )
            )
            coEvery { repository.getArtDetails("id") } returns apiResponse

            val expected = listOf(
                flowOf(NetworkResult.Loading<ArtDetails>()),
                flowOf(NetworkResult.Success(data = apiResponse.artDetailsEntity.toArtDetails()))
            )

            val actual = getArtDetails.invoke("id")

            assertEquals(expected[0].first().data, actual.first().data)
            assertEquals(expected[1].first().data, actual.last().data)
            coVerify(exactly = 1) { repository.getArtDetails(any()) }
        }

    @Test
    fun `when get art details failure with http error, verify NetworkResult Loading and Error received with correct data`() =
        runTest {
            val error =
                HttpException(Response.error<ResponseBody>(404, "any content".toResponseBody()))

            coEvery { repository.getArtDetails(any()) } throws error

            val expected = listOf(
                flowOf(NetworkResult.Loading<ArtDetails>()),
                flowOf(error.localizedMessage?.let { NetworkResult.Error<ArtDetails>(message = it) })
            )

            val actual = getArtDetails.invoke("id")

            assertEquals(expected[0].first()?.data, actual.first().data)
            assertEquals(expected[1].first()?.message, actual.last().message)
            coVerify(exactly = 1) { repository.getArtDetails(any()) }
        }

    @Test
    fun `when get art details failure with io error, verify NetworkResult Loading and Error received with correct data`() =
        runTest {
            val error = IOException("any error")

            coEvery { repository.getArtDetails(any()) } throws error

            val expected = listOf(
                flowOf(NetworkResult.Loading<ArtDetails>()),
                flowOf(error.message?.let { NetworkResult.Error<ArtDetails>(message = it) })
            )

            val actual = getArtDetails.invoke("id")

            assertEquals(expected[0].first()?.data, actual.first().data)
            assertEquals(expected[1].first()?.message, actual.last().message)
            coVerify(exactly = 1) { repository.getArtDetails(any()) }
        }
}