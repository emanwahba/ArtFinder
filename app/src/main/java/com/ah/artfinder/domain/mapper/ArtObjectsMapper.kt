package com.ah.artfinder.domain.mapper

import com.ah.artfinder.data.remote.entity.ArtObjectEntity
import com.ah.artfinder.domain.model.ArtObject

fun ArtObjectEntity.toArtObject() = ArtObject(
    id = this.id,
    objectNumber = this.objectNumber,
    title = this.title,
    longTitle = this.longTitle,
    artist = this.principalOrFirstMaker,
    imageUrl = this.webImage?.url
)