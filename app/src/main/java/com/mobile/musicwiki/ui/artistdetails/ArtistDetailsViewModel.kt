package com.mobile.musicwiki.ui.artistdetails

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.musicwiki.R
import com.mobile.musicwiki.service.model.ArtistsDetailsResponse
import com.mobile.musicwiki.service.repository.MusicWikiRepository
import com.mobile.musicwiki.service.utility.NetworkHelper
import com.mobile.musicwiki.service.utility.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response

class ArtistDetailsViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val repository: MusicWikiRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    var isApiLoadedOnce = false

    // live data for artist details
    private val _artistDetailsMutableLiveData = MutableLiveData<Resource<ArtistsDetailsResponse>>()
    val artistLiveData: LiveData<Resource<ArtistsDetailsResponse>>
        get() = _artistDetailsMutableLiveData

    fun getArtistDetails(artistName: String) {
        viewModelScope.launch {
            with(_artistDetailsMutableLiveData) {
                postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    try {
                        setData(repository.getArtistDetails(artistName))
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

    private fun setData(response: Response<ArtistsDetailsResponse>) {
        if (response.isSuccessful) {
            // add data to live data
            viewModelScope.launch {
                response.body().let {
                    it?.let {
                        _artistDetailsMutableLiveData.postValue(Resource.success(it))
                    }
                }
            }
        } else {
            _artistDetailsMutableLiveData.postValue(
                Resource.error(
                    response.errorBody().toString(),
                    null
                )
            )
        }
    }
}