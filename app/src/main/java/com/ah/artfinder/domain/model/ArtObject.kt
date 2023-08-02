package com.ah.artfinder.domain.model

data class ArtObject(

    val id: String,
    val objectNumber: String,
    val title: String,
    val longTitle: String,
    val artist: String,
    val imageUrl: String?
)