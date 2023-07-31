package com.ah.artfinder.data.remote.entity

import com.google.gson.annotations.SerializedName

data class LinksEntity(

    @SerializedName("self")
    val self: String,

    @SerializedName("web")
    val web: String
)