package com.mobile.musicwiki.service.model

data class GenresResponse(val toptags: Tags)

data class Tags(val tag: ArrayList<Tag>)

data class Tag(val name: String, val count: Long, val reach: Long)
