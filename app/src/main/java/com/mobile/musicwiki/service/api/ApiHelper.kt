package com.mobile.musicwiki.service.api

import com.mobile.musicwiki.service.model.*
import retrofit2.Response

interface ApiHelper {
    suspend fun getGenres(): Response<GenresResponse>

    suspend fun getGenresDetails(genreName: String): Response<GenresDetailsResponse>

    suspend fun getAlbums(genreName: String): Response<AlbumsResponse>

    suspend fun getArtists(genreName: String): Response<ArtistsResponse>

    suspend fun getTracks(genreName: String): Response<TracksResponse>
}