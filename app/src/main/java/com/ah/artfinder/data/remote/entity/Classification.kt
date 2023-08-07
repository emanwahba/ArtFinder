package com.ah.artfinder.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Classification(

    @SerializedName("iconClassDescription")
    val iconClassDescription: List<String>
)