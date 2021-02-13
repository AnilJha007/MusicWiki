package com.mobile.musicwiki.service.model

data class GenresResponse(val toptags: Genres)

data class Genres(val tag: ArrayList<Genre>)

data class Genre(val name: String, val count: Long, val reach: Long)
