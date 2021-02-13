package com.mobile.musicwiki.service.model

import com.google.gson.annotations.SerializedName

data class AlbumsResponse(val albums: Albums)

data class Albums(val album: ArrayList<Album>)

data class Album(
    val name: String,
    val mbid: String,
    val url: String,
    val artist: Artist,
    val image: ArrayList<Image>?
)

data class Image(@SerializedName("#text") val text: String, val size: String)
