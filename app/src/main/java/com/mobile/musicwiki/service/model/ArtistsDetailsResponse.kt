package com.mobile.musicwiki.service.model

data class ArtistsDetailsResponse(val artist: ArtistDetails)

data class ArtistDetails(
    val name: String,
    val mbid: String,
    val url: String,
    val streamable: String,
    val image: ArrayList<Image>?,
    val stats: Stats,
    val bio: Bio
)

data class Stats(val listeners: Long, val playcount: Long)

data class Bio(val published: String, val summary: String, val content: String)
