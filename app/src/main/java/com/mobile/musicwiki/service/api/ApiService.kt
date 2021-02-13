package com.mobile.musicwiki.service.api

import com.mobile.musicwiki.BuildConfig
import com.mobile.musicwiki.service.model.*
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

    @GET("2.0/")
    suspend fun getGenresDetails(
        @Query("method") method: String = ApiConstants.GENRES_DETAILS_METHOD,
        @Query("tag") tag: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = "json"
    ): Response<GenresDetailsResponse>

    @GET("2.0/")
    suspend fun getAlbums(
        @Query("method") method: String = ApiConstants.ALBUMS_METHOD,
        @Query("tag") tag: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = "json"
    ): Response<AlbumsResponse>

    @GET("2.0/")
    suspend fun getArtists(
        @Query("method") method: String = ApiConstants.ARTISTS_METHOD,
        @Query("tag") tag: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = "json"
    ): Response<ArtistsResponse>

    @GET("2.0/")
    suspend fun getTracks(
        @Query("method") method: String = ApiConstants.TRACKS_METHOD,
        @Query("tag") tag: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = "json"
    ): Response<TracksResponse>

    @GET("2.0/")
    suspend fun getAlbumDetails(
        @Query("method") method: String = ApiConstants.ALBUM_DETAILS_METHOD,
        @Query("artist") artist: String,
        @Query("album") album: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = "json"
    ): Response<AlbumDetailsResponse>
}