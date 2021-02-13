package com.mobile.musicwiki.service.api

import com.mobile.musicwiki.service.model.GenresDetailsResponse
import com.mobile.musicwiki.service.model.GenresResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getGenres(): Response<GenresResponse> = apiService.getGenres()

    override suspend fun getGenresDetails(genreName: String): Response<GenresDetailsResponse> =
        apiService.getGenresDetails(tag = genreName)
}