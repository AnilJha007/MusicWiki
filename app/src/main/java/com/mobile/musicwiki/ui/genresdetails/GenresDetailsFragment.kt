package com.mobile.musicwiki.ui.genresdetails

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseFragment
import com.mobile.musicwiki.service.model.GenreDetails
import com.mobile.musicwiki.service.utility.ApiStatus
import com.mobile.musicwiki.utils.setTextOrHide
import com.mobile.musicwiki.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_genres_details.*

@AndroidEntryPoint
class GenresDetailsFragment : BaseFragment() {

    private lateinit var genreName: String
    private lateinit var spotsDialog: AlertDialog
    private val genreDetailsViewModel: GenreDetailsViewModel by viewModels()

    companion object {
        const val GENRE_NAME = "genreName"
    }

    override fun setPageTitle() {
        activity?.title = genreName
    }

    override fun getLayoutResourceId() = R.layout.fragment_genres_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreName = requireArguments().getString(GENRE_NAME)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitStateData()
        initListener()
        initViewModel()
    }

    private fun setInitStateData() {
        spotsDialog = getAlertDialog(requireContext())
    }

    private fun initViewModel() {
        genreDetailsViewModel.genreDetailsLiveData.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                ApiStatus.LOADING -> {
                    if (::spotsDialog.isInitialized && !genreDetailsViewModel.isApiLoadedOnce) {
                        genreDetailsViewModel.isApiLoadedOnce = true
                        spotsDialog.show()
                    }
                }
                ApiStatus.SUCCESS -> {
                    if (::spotsDialog.isInitialized && spotsDialog.isShowing)
                        spotsDialog.hide()

                    res.data?.let {
                        setData(it)
                    }
                }
                ApiStatus.ERROR -> {
                    if (::spotsDialog.isInitialized && spotsDialog.isShowing)
                        spotsDialog.hide()
                    genreDetailsParent.snackBar(res.message!!)
                }
            }
        }
        genreDetailsViewModel.getGenreDetails(genreName)
    }

    private fun setData(it: GenreDetails) {
        tvGenreDetails.setTextOrHide(it.wiki.summary, true)
    }

    private fun initListener() {}
}