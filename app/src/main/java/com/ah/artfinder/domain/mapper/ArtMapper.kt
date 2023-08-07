package com.ah.artfinder.domain.mapper

import com.ah.artfinder.data.remote.entity.ArtDetailsEntity
import com.ah.artfinder.data.remote.entity.ArtEntity
import com.ah.artfinder.domain.model.ArtDetails
import com.ah.artfinder.domain.model.Art

fun ArtEntity.toArt() = Art(
    id = this.id,
    objectNumber = this.objectNumber,
    title = this.title,
    longTitle = this.longTitle,
    artist = this.principalOrFirstMaker,
    imageUrl = this.webImage?.url
)

fun ArtDetailsEntity.toArtDetails() = ArtDetails(
    id = this.id,
    objectNumber = this.objectNumber,
    title = this.title,
    description = this.description,
    artist = this.principalMaker,
    location = this.location,
    materials = this.materials?.joinToString(", "),
    imageUrl = this.webImage?.url
)