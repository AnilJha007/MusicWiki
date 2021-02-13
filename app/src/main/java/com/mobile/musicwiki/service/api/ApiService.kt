package com.mobile.musicwiki.service.api

import com.mobile.musicwiki.BuildConfig
import com.mobile.musicwiki.service.model.GenresResponse
import com.mobile.musicwiki.service.utility.ApiConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("2.0/")
    suspend fun getGenres(
        @Query("method") method: String = ApiConstants.GENRES_METHOD,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = "json"
    ): Response<GenresResponse>

}