package com.mobile.musicwiki.ui.genredetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseFragment
import com.mobile.musicwiki.service.model.AlbumsResponse
import com.mobile.musicwiki.service.utility.ApiStatus
import com.mobile.musicwiki.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_albums.*

@AndroidEntryPoint
class AlbumsFragment : BaseFragment() {

    companion object {
        const val TITLE = "ALBUMS"
        fun newInstance(): AlbumsFragment = AlbumsFragment()
    }

    private lateinit var itemAdapter: ItemAdapter
    private val albumsViewModel: AlbumsViewModel by viewModels()

    override fun getLayoutResourceId() = R.layout.fragment_albums

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initAdapter()
        initViewModel()
    }

    private fun initAdapter() {
        itemAdapter = ItemAdapter {}
        rvAlbums.adapter = itemAdapter
    }

    private fun initViewModel() {
        albumsViewModel.albumsLiveData.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                ApiStatus.LOADING -> {
                }
                ApiStatus.SUCCESS -> {
                    res.data?.let {
                        setData(it)
                    }
                }
                ApiStatus.ERROR -> {
                    albumsParent.snackBar(res.message!!)
                }
            }
        }
        albumsViewModel.getAlbums((this.parentFragment as GenresDetailsFragment).genreName)
    }

    private fun setData(it: AlbumsResponse) {
        val data = arrayListOf<Item>()
        it.albums.album.forEach {
            data.add(Item(it.name, it.artist.name, it.image?.lastOrNull()?.text))
        }
        itemAdapter.updateData(data)
    }

    private fun initListener() {}
}