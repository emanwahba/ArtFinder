package com.ah.artfinder.presentation.artdetails

import androidx.lifecycle.SavedStateHandle
import com.ah.artfinder.MainDispatcherRule
import com.ah.artfinder.domain.model.ArtDetails
import com.ah.artfinder.domain.usecase.GetArtDetails
import com.ah.artfinder.presentation.ScreenParameter
import com.ah.artfinder.util.NetworkResult
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArtDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var getArtDetailsUseCase: GetArtDetails

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: ArtDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        every { savedStateHandle.get<String>(any()) } returns null

        viewModel = ArtDetailsViewModel(getArtDetailsUseCase, savedStateHandle)
    }

    @Test
    fun `when get art details with valid id, verify NetworkResult Success received with correct data`() =
        runTest {
            val artDetails = ArtDetails(
                "id",
                "object number",
                "title",
                "description",
                "artist",
                "location",
                "materials",
                null
            )

            every { getArtDetailsUseCase.invoke(any()) } returns flowOf(NetworkResult.Success(data = artDetails))

            viewModel.getDetails("id")

            assertEquals(artDetails, viewModel.state.value.artDetails)
            verify(exactly = 1) { getArtDetailsUseCase(any()) }
        }

    @Test
    fun `when get art details with invalid id, verify NetworkResult Error received with correct data`() =
        runTest {
            val errorMessage = "some error"

            every { getArtDetailsUseCase.invoke(any()) } returns flowOf(
                NetworkResult.Error(
                    errorMessage
                )
            )

            viewModel.getDetails("id")

            assertEquals(errorMessage, viewModel.state.value.error)
            verify(exactly = 1) { getArtDetailsUseCase(any()) }
        }

    @Test
    fun `when get art details, verify NetworkResult Loading received`() = runTest {
        every { getArtDetailsUseCase.invoke(any()) } returns flowOf(NetworkResult.Loading())

        viewModel.getDetails("id")

        assertTrue(viewModel.state.value.isLoading)
        verify(exactly = 1) { getArtDetailsUseCase(any()) }
    }

    @Test
    fun `when view model with art id, verify state object holds it`() = runTest {
        val artId = "1"
        every { savedStateHandle.get<String>(ScreenParameter.ArtId.key) } returns artId
        every { getArtDetailsUseCase.invoke(any()) } returns flowOf(NetworkResult.Loading())

        viewModel = ArtDetailsViewModel(getArtDetailsUseCase, savedStateHandle)

        assertEquals(artId, viewModel.artId.value)
    }

    @Test
    fun `when view model without art id, verify state object empty`() = runTest {
        every { savedStateHandle.get<String>(ScreenParameter.ArtId.key) } returns null
        every { getArtDetailsUseCase.invoke(any()) } returns flowOf(NetworkResult.Loading())

        viewModel = ArtDetailsViewModel(getArtDetailsUseCase, savedStateHandle)

        assertTrue(viewModel.artId.value.isEmpty())
    }
}