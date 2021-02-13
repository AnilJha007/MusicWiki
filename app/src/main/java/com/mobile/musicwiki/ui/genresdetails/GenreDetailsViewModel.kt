package com.mobile.musicwiki.ui.genresdetails

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.musicwiki.R
import com.mobile.musicwiki.service.model.GenreDetails
import com.mobile.musicwiki.service.model.GenresDetailsResponse
import com.mobile.musicwiki.service.repository.MusicWikiRepository
import com.mobile.musicwiki.service.utility.NetworkHelper
import com.mobile.musicwiki.service.utility.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response

class GenreDetailsViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val repository: MusicWikiRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    var isApiLoadedOnce = false

    // live data for genre details
    private val _genreDetailsMutableLiveData = MutableLiveData<Resource<GenreDetails>>()
    val genreDetailsLiveData: LiveData<Resource<GenreDetails>>
        get() = _genreDetailsMutableLiveData

    fun getGenreDetails(genreName: String) {
        viewModelScope.launch {
            with(_genreDetailsMutableLiveData) {
                postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    try {
                        setGenreDetailsData(repository.getGenreDetails(genreName))
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

    private fun setGenreDetailsData(response: Response<GenresDetailsResponse>) {
        if (response.isSuccessful) {
            // add data to live data
            viewModelScope.launch {
                response.body().let {
                    it?.let {
                        _genreDetailsMutableLiveData.postValue(Resource.success(it.tag))
                    }
                }
            }
        } else {
            _genreDetailsMutableLiveData.postValue(
                Resource.error(
                    response.errorBody().toString(),
                    null
                )
            )
        }
    }
}