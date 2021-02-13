package com.mobile.musicwiki.service.model

data class GenresDetailsResponse(val tag: GenreDetails)

data class GenreDetails(val name: String, val count: Long, val reach: Long, val wiki: GenreWiki?)

data class GenreWiki(val summary: String?, val content: String)
