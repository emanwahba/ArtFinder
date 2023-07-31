package com.ah.artfinder.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ArtApiResponse(

    @SerializedName("artObjects")
    val artObjectsEntity: List<ArtObjectEntity>,

    @SerializedName("count")
    val count: Int,

    @SerializedName("elapsedMilliseconds")
    val elapsedMilliseconds: Int
)