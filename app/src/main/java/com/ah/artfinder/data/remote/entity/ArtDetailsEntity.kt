package com.ah.artfinder.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ArtDetailsEntity(

    @SerializedName("id")
    val id: String,

    @SerializedName("objectNumber")
    val objectNumber: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("titles")
    val titles: List<String>,

    @SerializedName("location")
    val location: String?,

    @SerializedName("materials")
    val materials: List<String>?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("webImage")
    val webImage: WebImage?,

    @SerializedName("label")
    val label: Label,

    @SerializedName("language")
    val language: String,

    @SerializedName("principalMaker")
    val principalMaker: String?,

    @SerializedName("scLabelLine")
    val scLabelLine: String,

    @SerializedName("classification")
    val classification: Classification,

    @SerializedName("dating")
    val dating: Dating
)