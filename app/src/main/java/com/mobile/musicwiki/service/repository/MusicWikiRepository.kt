package com.mobile.musicwiki.service.repository

import com.mobile.musicwiki.service.api.ApiHelper
import javax.inject.Inject

class MusicWikiRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getGenres() = apiHelper.getGenres()

    suspend fun getGenreDetails(genreName: String) = apiHelper.getGenresDetails(genreName)

    suspend fun getAlbums(genreName: String) = apiHelper.getAlbums(genreName)

    suspend fun getArtists(genreName: String) = apiHelper.getArtists(genreName)

    suspend fun getTracks(genreName: String) = apiHelper.getTracks(genreName)

    suspend fun getAlbumDetails(albumName: String, artistName: String) =
        apiHelper.getAlbumDetails(albumName, artistName)

    suspend fun getArtistDetails(artistName: String) =
        apiHelper.getArtistDetails(artistName)
}