package com.mobile.musicwiki.ui.genredetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class GenreDetailsPagerAdapter(fm: FragmentManager, private val count: Int = 3) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() = count

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AlbumsFragment.newInstance()
            1 -> ArtistsFragment.newInstance()
            2 -> TracksFragment.newInstance()
            else -> AlbumsFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> AlbumsFragment.ALBUMS
            1 -> ArtistsFragment.ARTISTS
            2 -> TracksFragment.TRACKS
            else -> AlbumsFragment.ALBUMS
        }
    }
}