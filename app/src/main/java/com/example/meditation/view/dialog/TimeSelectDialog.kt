package com.example.meditation.view.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.meditation.R
import com.example.meditation.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TimeSelectDialog: DialogFragment() {

    var selectedItemId = 0
    //private val viewModel: MainViewModel by activityViewModels()
    private val viewModel: MainViewModel by sharedViewModel()

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(activity!!).apply {
            setTitle(R.string.select_time)
            setSingleChoiceItems(R.array.time_list, selectedItemId) { dialog, which ->
                selectedItemId = which
                viewModel.setTime(selectedItemId)
                dialog.dismiss()
            }

        }.create()

        return dialog
    }
}