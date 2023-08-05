package com.ah.artfinder.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtObject(
    val id: String,
    val objectNumber: String,
    val title: String,
    val longTitle: String,
    val artist: String,
    val imageUrl: String?
) : Parcelable