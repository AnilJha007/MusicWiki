package com.mobile.musicwiki.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobile.musicwiki.R
import dmax.dialog.SpotsDialog

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(getLayoutResourceId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setPageTitle()
    }

    /* override this method in fragment to update page title*/
    abstract fun setPageTitle()

    /* override this method in fragment to attach the layout*/
    abstract fun getLayoutResourceId(): Int

    fun getAlertDialog(context: Context, cancelable: Boolean = false): AlertDialog {
        return SpotsDialog.Builder()
            .setContext(context)
            .setTheme(R.style.DialogTheme)
            .setCancelable(cancelable)
            .build()
    }
}