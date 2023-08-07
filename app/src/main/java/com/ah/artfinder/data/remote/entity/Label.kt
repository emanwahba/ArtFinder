package com.ah.artfinder.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Label(

    @SerializedName("date")
    val date: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("makerLine")
    val makerLine: String,

    @SerializedName("notes")
    val notes: Any,

    @SerializedName("title")
    val title: String
)