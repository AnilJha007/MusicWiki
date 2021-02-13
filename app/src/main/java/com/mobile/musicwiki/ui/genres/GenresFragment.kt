package com.mobile.musicwiki.ui.genres

import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenresFragment : BaseFragment() {

    override fun setPageTitle() {
        activity?.title = getString(R.string.app_name)
    }

    override fun getLayoutResourceId() = R.layout.fragment_genres
}