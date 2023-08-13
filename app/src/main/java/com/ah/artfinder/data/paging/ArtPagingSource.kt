package com.ah.artfinder.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ah.artfinder.data.remote.ArtApi
import com.ah.artfinder.data.remote.ArtApi.Companion.FIRST_PAGE_INDEX
import com.ah.artfinder.data.remote.entity.ArtEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ArtPagingSource @Inject constructor(
    private val api: ArtApi,
    private val searchQuery: String?,
    private val sortedBy: String?,
    private val pageSize: Int
) : PagingSource<Int, ArtEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtEntity> {
        val pageNumber = params.key ?: FIRST_PAGE_INDEX

        return try {
            val response = api.searchArt(
                searchQuery,
                sortedBy,
                pageNumber,
                params.loadSize
            )

            val prevKey = if (pageNumber == FIRST_PAGE_INDEX) null else pageNumber.minus(1)
            val nextKey = if (response.artListEntity.isEmpty()) {
                null
            } else {
                pageNumber + (params.loadSize / pageSize)
            }
            LoadResult.Page(
                data = response.artListEntity,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArtEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}