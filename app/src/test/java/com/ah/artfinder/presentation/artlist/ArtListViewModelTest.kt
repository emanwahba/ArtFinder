package com.ah.artfinder.presentation.artlist

import com.ah.artfinder.domain.usecase.SearchArtSortedByArtist
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArtListViewModelTest {

    @MockK
    private lateinit var searchArtUseCase: SearchArtSortedByArtist

    private lateinit var viewModel: ArtListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        every { searchArtUseCase.invoke(any(), any()) } returns mockk()

        viewModel = ArtListViewModel(searchArtUseCase)
    }

    @Test
    fun `when search art with keyword, verify correct query is sent`() {
        val keyword = "art"

        viewModel.searchArt(keyword)

        assertEquals(keyword, viewModel.query.value)
        verify(exactly = 1) { searchArtUseCase(keyword, any()) }
    }

    @Test
    fun `when search art without keyword, verify correct query is sent`() {
        viewModel.searchArt()

        assertEquals("", viewModel.query.value)
        // 1 from viewModel.init() and 1 from here
        verify(exactly = 2) { searchArtUseCase(null, any()) }
    }

    @Test
    fun `when search art with page size, verify correct page size is sent`() {
        val pageSize = 10

        viewModel.searchArt(pageSize = pageSize)

        verify(exactly = 1) { searchArtUseCase(any(), pageSize) }
    }

    @Test
    fun `when search art without page size, verify default page size is sent`() {
        viewModel.searchArt()

        // 1 from viewModel.init() and 1 from here
        verify(exactly = 2) { searchArtUseCase(any(), DEFAULT_PAGE_SIZE) }
    }
}