package com.mobile.musicwiki.service.repository

import com.mobile.musicwiki.service.api.ApiHelper
import javax.inject.Inject

class MusicWikiRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getGenres() = apiHelper.getGenres()
}