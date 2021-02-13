package com.mobile.musicwiki.ui.artistdetails

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseFragment
import com.mobile.musicwiki.service.model.ArtistsDetailsResponse
import com.mobile.musicwiki.service.utility.ApiStatus
import com.mobile.musicwiki.ui.genredetails.ArtistsFragment.Companion.ARTISTS
import com.mobile.musicwiki.utils.setImage
import com.mobile.musicwiki.utils.setTextOrHide
import com.mobile.musicwiki.utils.show
import com.mobile.musicwiki.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_artist_details.*

@AndroidEntryPoint
class ArtistDetailsFragment : BaseFragment() {

    lateinit var artistName: String
    private lateinit var spotsDialog: AlertDialog

    override fun setPageTitle() {
        activity?.title = artistName
    }

    private val artistDetailsViewModel: ArtistDetailsViewModel by viewModels()

    override fun getLayoutResourceId() = R.layout.fragment_artist_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artistName = requireArguments().getString(ARTISTS)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitStateData()
        initViewModel()
    }

    private fun setInitStateData() {
        spotsDialog = getAlertDialog(requireContext())
    }

    private fun initViewModel() {
        artistDetailsViewModel.artistLiveData.observe(viewLifecycleOwner) { res ->
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
                    artistDetailsParent.snackBar(res.message!!)
                }
            }
        }
        artistDetailsViewModel.getArtistDetails(artistName)
    }

    private fun setData(it: ArtistsDetailsResponse) {
        viewGroup.show()
        with(it.artist) {
            ivItem.setImage(image?.lastOrNull()?.text)
            ivItem.imageAlpha = 90
            tvTitle.setTextOrHide(name)
            tvPlaycountValue.setTextOrHide(coolFormat(stats.playcount.toDouble()))
            tvFollowersValue.setTextOrHide(coolFormat(stats.listeners.toDouble()))
            tvArtistDetails.setTextOrHide(bio.summary, true)
        }
    }

    private fun coolFormat(n: Double, iteration: Int = 0): String {
        val c = charArrayOf('K', 'M', 'B', 'T')

        val d = n.toLong() / 100 / 10.0
        val isRound =
            d * 10 % 10 == 0.0 //true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (if (d < 1000) //this determines the class, i.e. 'k', 'm' etc
            (if (d > 99.9 || isRound || !isRound && d > 9.99) //this decides whether to trim the decimals
                d.toInt() * 10 / 10 else d.toString() + "" // (int) d * 10 / 10 drops the decimal
                    ).toString() + "" + c.get(iteration) else coolFormat(d, iteration + 1))
    }
}