package com.mobile.musicwiki.service.api

import com.mobile.musicwiki.service.model.GenresResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getGenres(): Response<GenresResponse>
}