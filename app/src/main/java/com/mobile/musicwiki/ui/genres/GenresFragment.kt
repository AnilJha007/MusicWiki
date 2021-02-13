package com.mobile.musicwiki.ui.genres

import android.os.Bundle
import android.view.View
import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseFragment
import com.mobile.musicwiki.utils.customviews.DataTransform
import com.mobile.musicwiki.utils.customviews.TagView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_genres.*

@AndroidEntryPoint
class GenresFragment : BaseFragment() {

    override fun setPageTitle() {
        activity?.title = getString(R.string.app_name)
    }

    override fun getLayoutResourceId() = R.layout.fragment_genres

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        setItems()
    }

    private fun setItems() {
        val tagsData = arrayListOf(
            "Genres",
            "Genres",
            "Genres",
            "Genres",
            "Genres",
            "Genres Genres Genres",
            "Genres Genres"
        )
        (tagView as TagView<String>).setTags(tagsData, object : DataTransform<String> {
            override fun transfer(item: String): String {
                return item
            }
        })
        (tagView as TagView<String>).setClickListener(object : TagView.TagClickListener<String> {
            override fun onTagClick(item: String) {

            }
        })
    }

    private fun initListener() {
        btnChooseGenre.setOnClickListener {
            setGenresCountVisibility(it.isSelected)
            it.isSelected = !it.isSelected
        }
    }

    private fun setGenresCountVisibility(selected: Boolean) {

    }
}