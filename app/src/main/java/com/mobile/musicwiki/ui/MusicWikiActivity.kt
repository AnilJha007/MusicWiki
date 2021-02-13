package com.mobile.musicwiki.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.mobile.musicwiki.R
import com.mobile.musicwiki.base.BaseActivity
import kotlinx.android.synthetic.main.activity_music_wiki.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpNavActionBar()
    }

    override fun getLayoutResourceId() = R.layout.activity_music_wiki

    private fun setUpNavActionBar() {
        val appBarConfiguration = AppBarConfiguration.Builder(R.id.genresDest).build()
        setupActionBarWithNavController(
            (navHostFragment as NavHostFragment).navController,
            appBarConfiguration
        )
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}