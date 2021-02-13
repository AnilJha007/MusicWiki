package com.mobile.musicwiki.ui.genres

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.musicwiki.R
import com.mobile.musicwiki.service.model.GenresResponse
import com.mobile.musicwiki.service.model.Tags
import com.mobile.musicwiki.service.repository.MusicWikiRepository
import com.mobile.musicwiki.service.utility.NetworkHelper
import com.mobile.musicwiki.service.utility.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response

class GenresViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val repository: MusicWikiRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    // live data for genres list
    private val _genresMutableLiveData = MutableLiveData<Resource<Tags>>()
    val genresLiveData: LiveData<Resource<Tags>>
        get() = _genresMutableLiveData

    fun getGenres() {
        viewModelScope.launch {
            with(_genresMutableLiveData) {
                postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    try {
                        setGenresData(repository.getGenres())
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

    private fun setGenresData(response: Response<GenresResponse>) {
        if (response.isSuccessful) {
            // add data to live data
            viewModelScope.launch {
                response.body().let {
                    it?.let {
                        _genresMutableLiveData.postValue(Resource.success(it.toptags))
                    }
                }
            }
        } else {
            _genresMutableLiveData.postValue(Resource.error(response.errorBody().toString(), null))
        }
    }
}