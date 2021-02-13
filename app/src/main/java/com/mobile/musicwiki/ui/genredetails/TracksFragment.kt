package com.mobile.musicwiki.ui.genredetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseFragment
import com.mobile.musicwiki.service.model.TracksResponse
import com.mobile.musicwiki.service.utility.ApiStatus
import com.mobile.musicwiki.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracks.*

@AndroidEntryPoint
class TracksFragment : BaseFragment() {

    companion object {
        const val TITLE = "TRACKS"
        fun newInstance(): TracksFragment = TracksFragment()
    }

    private lateinit var itemAdapter: ItemAdapter
    private val tracksViewModel: TracksViewModel by viewModels()

    override fun getLayoutResourceId() = R.layout.fragment_tracks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initViewModel()
    }


    private fun initAdapter() {
        itemAdapter = ItemAdapter {}
        rvTracks.adapter = itemAdapter
    }

    private fun initViewModel() {
        tracksViewModel.tracksLiveData.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                ApiStatus.LOADING -> {
                }
                ApiStatus.SUCCESS -> {
                    res.data?.let {
                        setData(it)
                    }
                }
                ApiStatus.ERROR -> {
                    tracksParent.snackBar(res.message!!)
                }
            }
        }
        tracksViewModel.getTracks((this.parentFragment as GenresDetailsFragment).genreName)
    }

    private fun setData(it: TracksResponse) {
        val data = arrayListOf<Item>()
        it.tracks.track.forEach {
            data.add(Item(it.name, it.artist.name, it.image?.lastOrNull()?.text))
        }
        itemAdapter.updateData(data)
    }
}