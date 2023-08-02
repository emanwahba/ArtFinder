package com.ah.artfinder.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ArtObjectEntity(

    @SerializedName("hasImage")
    val hasImage: Boolean,

    @SerializedName("headerImage")
    val headerImage: HeaderImageEntity,

    @SerializedName("id")
    val id: String,

    @SerializedName("links")
    val links: LinksEntity,

    @SerializedName("longTitle")
    val longTitle: String,

    @SerializedName("objectNumber")
    val objectNumber: String,

    @SerializedName("permitDownload")
    val permitDownload: Boolean,

    @SerializedName("principalOrFirstMaker")
    val principalOrFirstMaker: String,

    @SerializedName("productionPlaces")
    val productionPlaces: List<String>,

    @SerializedName("showImage")
    val showImage: Boolean,

    @SerializedName("title")
    val title: String,

    @SerializedName("webImage")
    val webImage: WebImageEntity?
)