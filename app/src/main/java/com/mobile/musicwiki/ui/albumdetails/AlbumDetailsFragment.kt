package com.mobile.musicwiki.ui.albumdetails

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseFragment
import com.mobile.musicwiki.service.model.AlbumDetailsResponse
import com.mobile.musicwiki.service.utility.ApiStatus
import com.mobile.musicwiki.ui.genredetails.AlbumsFragment.Companion.ALBUMS
import com.mobile.musicwiki.ui.genredetails.ArtistsFragment.Companion.ARTISTS
import com.mobile.musicwiki.utils.setImage
import com.mobile.musicwiki.utils.setTextOrHide
import com.mobile.musicwiki.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_album_details.*

@AndroidEntryPoint
class AlbumDetailsFragment : BaseFragment() {

    lateinit var albumName: String
    lateinit var artistName: String
    private lateinit var spotsDialog: AlertDialog

    override fun setPageTitle() {
        activity?.title = albumName
    }

    private val albumDetailsViewModel: AlbumDetailsViewModel by viewModels()

    override fun getLayoutResourceId() = R.layout.fragment_album_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumName = requireArguments().getString(ALBUMS)!!
        artistName = requireArguments().getString(ARTISTS)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitStateData()
        initViewModel()
    }

    private fun setInitStateData() {
        spotsDialog = getAlertDialog(requireContext())
    }

    private fun initViewModel() {
        albumDetailsViewModel.albumsLiveData.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                ApiStatus.LOADING -> {
                    if (::spotsDialog.isInitialized && !albumDetailsViewModel.isApiLoadedOnce) {
                        albumDetailsViewModel.isApiLoadedOnce = true
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
                    albumDetailsParent.snackBar(res.message!!)
                }
            }
        }
        albumDetailsViewModel.getAlbumDetails(albumName, artistName)
    }

    private fun setData(it: AlbumDetailsResponse) {
        with(it.album) {
            ivItem.setImage(image?.lastOrNull()?.text)
            tvTitle.setTextOrHide(name)
            tvSubtitle.setTextOrHide(artist)
            tvAlbumDetails.setTextOrHide(wiki?.summary, true)
        }
    }
}