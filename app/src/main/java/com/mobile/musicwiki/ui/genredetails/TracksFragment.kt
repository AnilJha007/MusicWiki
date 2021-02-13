package com.mobile.musicwiki.ui.genredetails

import android.os.Bundle
import android.view.View
import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TracksFragment : BaseFragment() {

    companion object {
        const val TITLE = "TRACKS"
        fun newInstance(): TracksFragment = TracksFragment()
    }

    override fun getLayoutResourceId() = R.layout.fragment_tracks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitStateData()
        initListener()
        initViewModel()
    }

    private fun setInitStateData() {}

    private fun initViewModel() {}

    private fun initListener() {}
}