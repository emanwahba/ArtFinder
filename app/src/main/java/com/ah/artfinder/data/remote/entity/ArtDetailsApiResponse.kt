package com.ah.artfinder.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ArtDetailsApiResponse(

    @SerializedName("artObject")
    val artDetailsEntity: ArtDetailsEntity
)