package com.mobile.musicwiki.ui.genres

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseFragment
import com.mobile.musicwiki.customviews.DataTransform
import com.mobile.musicwiki.customviews.TagView
import com.mobile.musicwiki.service.model.Tags
import com.mobile.musicwiki.service.utility.ApiStatus
import com.mobile.musicwiki.utils.show
import com.mobile.musicwiki.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_genres.*

@AndroidEntryPoint
class GenresFragment : BaseFragment() {

    private lateinit var spotsDialog: AlertDialog
    private var _tags: ArrayList<String> = arrayListOf()
    private val genresViewModel: GenresViewModel by viewModels()

    override fun setPageTitle() {
        activity?.title = getString(R.string.app_name)
    }

    override fun getLayoutResourceId() = R.layout.fragment_genres

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
        genresViewModel.genresLiveData.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                ApiStatus.LOADING -> {
                    if (::spotsDialog.isInitialized) {
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
                    genresParent.snackBar(res.message!!)
                }
            }
        }
        genresViewModel.getGenres()
    }

    private fun setData(it: Tags) {
        it.tag.forEach { _tags.add(it.name) }
        setTags()
        tagGroup.show()
    }

    private fun initListener() {
        btnChooseGenre.setOnClickListener {
            it.isSelected = !it.isSelected
            setTags(it.isSelected)
        }

        (tagView as TagView<String>).setClickListener(object : TagView.TagClickListener<String> {
            override fun onTagClick(item: String) {

            }
        })
    }

    private fun setTags(selected: Boolean = false) {
        with(tagView as TagView<String>) {
            clear()
            setTags(if (selected) _tags else _tags.take(10), object : DataTransform<String> {
                override fun transfer(item: String) = item
            })
        }
    }
}