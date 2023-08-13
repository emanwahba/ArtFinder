package com.ah.artfinder.presentation.artlist.model

import com.ah.artfinder.domain.model.Art

sealed class ArtListUiModel {
    class ArtModel(val art: Art) : ArtListUiModel()
    class SeparatorModel(val description: String) : ArtListUiModel()
}