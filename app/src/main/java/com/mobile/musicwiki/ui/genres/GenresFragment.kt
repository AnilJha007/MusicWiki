package com.mobile.musicwiki.ui.genres

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseFragment
import com.mobile.musicwiki.service.model.Genres
import com.mobile.musicwiki.utils.DataTransform
import com.mobile.musicwiki.widgets.TagView
import com.mobile.musicwiki.service.utility.ApiStatus
import com.mobile.musicwiki.ui.genredetails.GenresDetailsFragment.Companion.GENRE_NAME
import com.mobile.musicwiki.utils.show
import com.mobile.musicwiki.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_genres.*

@AndroidEntryPoint
class GenresFragment : BaseFragment() {

    private lateinit var spotsDialog: AlertDialog
    private var _genres: ArrayList<String> = arrayListOf()
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
                    if (::spotsDialog.isInitialized && !genresViewModel.isApiLoadedOnce) {
                        genresViewModel.isApiLoadedOnce = true
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

    private fun setData(it: Genres) {
        _genres.clear()
        it.tag.forEach { _genres.add(it.name) }
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
                findNavController().navigate(R.id.genresToGenresDetails, Bundle().apply {
                    putString(GENRE_NAME, item)
                })
            }
        })
    }

    private fun setTags(selected: Boolean = false) {
        with(tagView as TagView<String>) {
            clear()
            setTags(if (selected) _genres else _genres.take(10), object : DataTransform<String> {
                override fun transfer(item: String) = item
            })
        }
    }
}