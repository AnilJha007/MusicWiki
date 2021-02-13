package com.mobile.musicwiki.ui.genresdetails

import android.os.Bundle
import android.view.View
import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : BaseFragment() {

    override fun getLayoutResourceId() = R.layout.fragment_albums

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