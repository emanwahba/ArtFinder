package com.ah.artfinder.presentation.artdetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ah.artfinder.domain.usecase.GetArtDetails
import com.ah.artfinder.presentation.ScreenParameter
import com.ah.artfinder.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ArtDetailsViewModel @Inject constructor(
    private val getArtDetailsUseCase: GetArtDetails,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ArtDetailsState())
    val state: State<ArtDetailsState> = _state

    private val _artId = mutableStateOf("")
    val artId: State<String> = _artId

    init {
        savedStateHandle.get<String>(ScreenParameter.ArtId.key)?.let { artId ->
            _artId.value = artId
            getDetails(artId)
        }
    }

    fun getDetails(id: String) {

        getArtDetailsUseCase(id = id).onEach { result ->
            when (result) {
                is NetworkResult.Success -> {
                    _state.value = ArtDetailsState(
                        artDetails = result.data
                    )
                }

                is NetworkResult.Error -> {
                    _state.value = ArtDetailsState(
                        error = result.message
                    )
                }

                is NetworkResult.Loading -> {
                    _state.value = ArtDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}