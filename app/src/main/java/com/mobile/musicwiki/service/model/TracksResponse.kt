package com.mobile.musicwiki.service.model

data class TracksResponse(val tracks: Tracks)

data class Tracks(val track: ArrayList<Track>)

data class Track(
    val name: String,
    val duration: String,
    val mbid: String,
    val url: String,
    val artist: Artist,
    val image: ArrayList<Image>?
)
