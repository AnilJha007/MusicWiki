package com.mobile.musicwiki.ui.genredetails

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.musicwiki.R
import com.mobile.musicwiki.service.model.ArtistsResponse
import com.mobile.musicwiki.service.repository.MusicWikiRepository
import com.mobile.musicwiki.service.utility.NetworkHelper
import com.mobile.musicwiki.service.utility.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response

class ArtistsViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val repository: MusicWikiRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    // live data for artists
    private val _artistsMutableLiveData = MutableLiveData<Resource<ArtistsResponse>>()
    val artistsLiveData: LiveData<Resource<ArtistsResponse>>
        get() = _artistsMutableLiveData

    fun getArtists(genreName: String) {
        viewModelScope.launch {
            with(_artistsMutableLiveData) {
                postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    try {
                        setData(repository.getArtists(genreName))
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

    private fun setData(response: Response<ArtistsResponse>) {
        if (response.isSuccessful) {
            // add data to live data
            viewModelScope.launch {
                response.body().let {
                    it?.let {
                        _artistsMutableLiveData.postValue(Resource.success(it))
                    }
                }
            }
        } else {
            _artistsMutableLiveData.postValue(
                Resource.error(
                    response.errorBody().toString(),
                    null
                )
            )
        }
    }
}