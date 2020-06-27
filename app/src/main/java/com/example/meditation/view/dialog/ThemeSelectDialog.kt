package com.example.meditation.view.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.GridLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meditation.MyApplication
import com.example.meditation.R
import com.example.meditation.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ThemeSelectDialog: DialogFragment() {


    val appContext = MyApplication.appContext
    private val themeList = MyApplication.themeList
    //private lateinit var viewModel: MainViewModel
    private val viewModel: MainViewModel by sharedViewModel()


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


            val recyclerView = RecyclerView(appContext)
            with(recyclerView) {
                layoutManager = GridLayoutManager(appContext,2)
                adapter = ThemeSelectAdapter(themeList, viewModel)
            }

        val dialog = AlertDialog.Builder(activity!!).apply {
            setTitle(R.string.select_theme)
            setView(recyclerView)
        }.create()
        viewModel.txtTheme.observe(activity!!, Observer {
            dialog.dismiss()
        })
        return dialog
    }
}