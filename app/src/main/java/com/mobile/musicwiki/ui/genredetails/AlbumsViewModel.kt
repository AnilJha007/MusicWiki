package com.mobile.musicwiki.ui.genredetails

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.musicwiki.R
import com.mobile.musicwiki.service.model.AlbumsResponse
import com.mobile.musicwiki.service.repository.MusicWikiRepository
import com.mobile.musicwiki.service.utility.NetworkHelper
import com.mobile.musicwiki.service.utility.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response

class AlbumsViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val repository: MusicWikiRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    // live data for albums
    private val _albumsMutableLiveData = MutableLiveData<Resource<AlbumsResponse>>()
    val albumsLiveData: LiveData<Resource<AlbumsResponse>>
        get() = _albumsMutableLiveData

    fun getAlbums(genreName: String) {
        viewModelScope.launch {
            with(_albumsMutableLiveData) {
                postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    try {
                        setGenreDetailsData(repository.getAlbums(genreName))
                    } catch (e: Exception) {
                        postValue(
                            Resource.error(context.getString(R.string.something_went_wrong), null)
                        )
                    }
                } else {
                    postValue(
                        Resource.error(context.getString(R.string.no_internet_error), null)
                    )
                }
            }
        }
    }

    private fun setGenreDetailsData(response: Response<AlbumsResponse>) {
        if (response.isSuccessful) {
            // add data to live data
            viewModelScope.launch {
                response.body().let {
                    it?.let {
                        _albumsMutableLiveData.postValue(Resource.success(it))
                    }
                }
            }
        } else {
            _albumsMutableLiveData.postValue(
                Resource.error(
                    response.errorBody().toString(),
                    null
                )
            )
        }
    }
}