package com.ah.artfinder.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ArtEntity(

    @SerializedName("id")
    val id: String,

    @SerializedName("longTitle")
    val longTitle: String,

    @SerializedName("objectNumber")
    val objectNumber: String,

    @SerializedName("principalOrFirstMaker")
    val principalOrFirstMaker: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("webImage")
    val webImage: WebImage?
)