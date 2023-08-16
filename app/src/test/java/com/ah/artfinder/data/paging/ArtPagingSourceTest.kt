package com.ah.artfinder.data.paging

import androidx.paging.PagingSource
import com.ah.artfinder.data.remote.ArtApi
import com.ah.artfinder.data.remote.entity.ArtApiResponse
import com.ah.artfinder.data.remote.entity.ArtEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ArtPagingSourceTest {

    @MockK
    private lateinit var api: ArtApi

    private val pageSize = 1

    private lateinit var artPagingSource: ArtPagingSource

    private val artListResponse = ArtApiResponse(
        artListEntity = listOf(
            ArtEntity(
                id = "id 1",
                objectNumber = "object number 1",
                title = "title",
                longTitle = "long title",
                principalOrFirstMaker = "artist",
                webImage = null
            )
        )
    )
    private val nextArtListResponse = ArtApiResponse(
        artListEntity = listOf(
            ArtEntity(
                id = "id 2",
                objectNumber = "object number 2",
                title = "title",
                longTitle = "long title",
                principalOrFirstMaker = "artist",
                webImage = null
            )
        ),
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        artPagingSource = ArtPagingSource(api, "", "", pageSize)
    }


    @Test
    fun `art paging source refresh - success`() = runTest {
        coEvery { api.searchArt(any(), any(), any(), any()) } returns artListResponse

        val expectedResult = PagingSource.LoadResult.Page(
            data = artListResponse.artListEntity,
            prevKey = null,
            nextKey = 1
        )
        assertEquals(
            expectedResult, artPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = pageSize,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `art paging source append - success`() = runTest {
        coEvery { api.searchArt(any(), any(), any(), any()) } returns nextArtListResponse

        val expectedResult = PagingSource.LoadResult.Page(
            data = nextArtListResponse.artListEntity,
            prevKey = 0,
            nextKey = 2
        )
        assertEquals(
            expectedResult, artPagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 1,
                    loadSize = pageSize,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `art paging source prepend - success`() = runTest {
        coEvery { api.searchArt(any(), any(), any(), any()) } returns artListResponse

        val expectedResult = PagingSource.LoadResult.Page(
            data = artListResponse.artListEntity,
            prevKey = null,
            nextKey = 1
        )
        assertEquals(
            expectedResult, artPagingSource.load(
                PagingSource.LoadParams.Prepend(
                    key = 0,
                    loadSize = pageSize,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `art paging source load - failure - http error`() = runTest {
        val error =
            HttpException(Response.error<ResponseBody>(404, "any content".toResponseBody()))

        coEvery { api.searchArt(any(), any(), any(), any()) } throws error

        val expectedResult = PagingSource.LoadResult.Error<Int, ArtEntity>(error)

        assertEquals(
            expectedResult, artPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = pageSize,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `art paging source load - failure - io error`() = runTest {
        val error = IOException("any error")

        coEvery { api.searchArt(any(), any(), any(), any()) } throws error

        val expectedResult = PagingSource.LoadResult.Error<Int, ArtEntity>(error)

        assertEquals(
            expectedResult, artPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = pageSize,
                    placeholdersEnabled = false
                )
            )
        )
    }
}