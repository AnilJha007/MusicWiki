package com.mobile.musicwiki.service.api

import com.mobile.musicwiki.service.model.*
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getGenres(): Response<GenresResponse> = apiService.getGenres()

    override suspend fun getGenresDetails(genreName: String): Response<GenresDetailsResponse> =
        apiService.getGenresDetails(tag = genreName)

    override suspend fun getAlbums(genreName: String): Response<AlbumsResponse> =
        apiService.getAlbums(tag = genreName)

    override suspend fun getArtists(genreName: String): Response<ArtistsResponse> =
        apiService.getArtists(tag = genreName)

    override suspend fun getTracks(genreName: String): Response<TracksResponse> =
        apiService.getTracks(tag = genreName)

    override suspend fun getAlbumDetails(
        albumName: String,
        artistName: String
    ): Response<AlbumDetailsResponse> =
        apiService.getAlbumDetails(artist = artistName, album = albumName)

    override suspend fun getArtistDetails(artistName: String): Response<ArtistsDetailsResponse> =
        apiService.getArtistDetails(artist = artistName)
}