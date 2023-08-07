package com.ah.artfinder.data.remote.entity

import com.google.gson.annotations.SerializedName

data class WebImage(

    @SerializedName("guid")
    val guid: String,

    @SerializedName("height")
    val height: Int,

    @SerializedName("offsetPercentageX")
    val offsetPercentageX: Int,

    @SerializedName("offsetPercentageY")
    val offsetPercentageY: Int,

    @SerializedName("url")
    val url: String,

    @SerializedName("width")
    val width: Int
)