package com.ah.artfinder.presentation

sealed class ScreenParameter(val key: String) {
    data object ArtObjectKey: ScreenParameter("artObject")
}
