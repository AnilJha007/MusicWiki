package com.mobile.musicwiki.service.model

data class AlbumDetailsResponse(val album: AlbumDetails)

data class AlbumDetails(
    val name: String,
    val mbid: String,
    val url: String,
    val artist: String,
    val listeners: String,
    val playcount: String,
    val tracks: Tracks,
    val tags: Genres,
    val wiki: GenreWiki?,
    val image: ArrayList<Image>?
)
