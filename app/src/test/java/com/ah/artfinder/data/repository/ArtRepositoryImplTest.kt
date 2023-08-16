package com.ah.artfinder.data.repository

import com.ah.artfinder.data.remote.ArtApi
import com.ah.artfinder.data.remote.entity.ArtDetailsApiResponse
import com.ah.artfinder.data.remote.entity.ArtDetailsEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArtRepositoryImplTest {

    @MockK
    private lateinit var api: ArtApi

    private lateinit var artRepository: ArtRepository

    private val artDetailsApiResponse = ArtDetailsApiResponse(
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

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        artRepository = ArtRepositoryImpl(api)
    }

    @Test
    fun `when get art details, verify correct api response received`() = runTest {
        coEvery { api.getArtDetails(any()) } returns artDetailsApiResponse

        val actual = artRepository.getArtDetails("id")

        assertEquals(artDetailsApiResponse, actual)
        coVerify(exactly = 1) { artRepository.getArtDetails(any()) }
    }
}