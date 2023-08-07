package com.ah.artfinder.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ArtApiResponse(

    @SerializedName("artObjects")
    val artListEntity: List<ArtEntity>
)