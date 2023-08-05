package com.ah.artfinder.presentation.artlist

import com.ah.artfinder.domain.model.ArtObject

data class ArtListState(
    val isLoading: Boolean = false,
    var artObjects: Map<String, List<ArtObject>> = emptyMap(),
    val error: String = ""
)