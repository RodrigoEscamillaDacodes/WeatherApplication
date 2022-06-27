package com.dacodes.weatherapp.core.presentation

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.dacodes.weatherapp.R

class LoaderDialog(private val activity: Activity) {

    private var dialog: Dialog? = null

    fun handleLoaderDialog(show: Boolean) {
        if (show) {
            showDialog()
        } else {
            hideLoader()
        }
    }

    private fun showDialog() {
        val isShowing = dialog?.isShowing ?: false
        if (!isShowing && !activity.isFinishing) {
            if (dialog == null) {
                dialog = Dialog(activity)
            }

            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.setCancelable(false)
            dialog?.setContentView(R.layout.dialog_laoder)
            dialog?.show()
        }
    }

    private fun hideLoader() {
        dialog?.dismiss()
        dialog = null
    }
}