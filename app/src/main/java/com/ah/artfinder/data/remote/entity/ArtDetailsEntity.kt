package com.ah.artfinder.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ArtDetailsEntity(

    @SerializedName("id")
    val id: String,

    @SerializedName("objectNumber")
    val objectNumber: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("location")
    val location: String?,

    @SerializedName("materials")
    val materials: List<String>?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("webImage")
    val webImage: WebImage?,

    @SerializedName("principalMaker")
    val principalMaker: String?
)