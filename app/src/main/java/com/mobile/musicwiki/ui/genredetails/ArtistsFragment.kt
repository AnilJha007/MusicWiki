package com.mobile.musicwiki.ui.genredetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseFragment
import com.mobile.musicwiki.service.model.ArtistsResponse
import com.mobile.musicwiki.service.utility.ApiStatus
import com.mobile.musicwiki.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_artists.*

@AndroidEntryPoint
class ArtistsFragment : BaseFragment() {

    companion object {
        const val ARTISTS = "ARTISTS"
        fun newInstance(): ArtistsFragment = ArtistsFragment()
    }

    private lateinit var itemAdapter: ItemAdapter
    private val artistsViewModel: ArtistsViewModel by viewModels()

    override fun getLayoutResourceId() = R.layout.fragment_artists

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initViewModel()
    }

    private fun initAdapter() {
        itemAdapter = ItemAdapter {}
        rvArtists.adapter = itemAdapter
    }

    private fun initViewModel() {
        artistsViewModel.artistsLiveData.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                ApiStatus.LOADING -> {
                }
                ApiStatus.SUCCESS -> {
                    res.data?.let {
                        setData(it)
                    }
                }
                ApiStatus.ERROR -> {
                    artistsParent.snackBar(res.message!!)
                }
            }
        }
        artistsViewModel.getArtists((this.parentFragment as GenresDetailsFragment).genreName)
    }

    private fun setData(it: ArtistsResponse) {
        val data = arrayListOf<Item>()
        it.topartists.artist.forEach {
            data.add(Item(it.name, url = it.image?.lastOrNull()?.text))
        }
        itemAdapter.updateData(data)
    }
}