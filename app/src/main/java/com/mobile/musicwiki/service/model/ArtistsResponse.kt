package com.mobile.musicwiki.service.model

data class ArtistsResponse(val topartists: TopArtists)

data class TopArtists(val artist: ArrayList<Artist>)

data class Artist(
    val name: String,
    val mbid: String,
    val url: String,
    val streamable: String,
    val image: ArrayList<Image>?
)
