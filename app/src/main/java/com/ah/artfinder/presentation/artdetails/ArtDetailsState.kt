package com.ah.artfinder.presentation.artdetails

import com.ah.artfinder.domain.model.ArtDetails

data class ArtDetailsState(
    val isLoading: Boolean = false,
    var artDetails: ArtDetails? = null,
    val error: String = ""
)