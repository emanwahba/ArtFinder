package com.ah.artfinder.presentation.artlist

import com.ah.artfinder.domain.model.Art

data class ArtListState(
    val isLoading: Boolean = false,
    var artMap: Map<String, List<Art>> = emptyMap(),
    val error: String = ""
)