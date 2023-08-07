package com.ah.artfinder.domain.model

data class ArtDetails(
    val id: String,
    val objectNumber: String,
    val title: String,
    val description: String?,
    val artist: String?,
    val location: String?,
    val materials: String?,
    val imageUrl: String?
)