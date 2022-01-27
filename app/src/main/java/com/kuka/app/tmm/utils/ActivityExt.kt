package com.kuka.app.tmm.utils

import androidx.appcompat.app.AppCompatActivity
import com.kuka.app.tmm.ui.progress.ProgressDialogFragment

fun AppCompatActivity.showDialogProgress(boolean: Boolean) {
    if (boolean) {
        val dialogProgressFragment = ProgressDialogFragment()
        dialogProgressFragment.show(supportFragmentManager, "dialogProgress2")
    } else {
        val prevDialogProgressFragment = supportFragmentManager.findFragmentByTag("dialogProgress2") as ProgressDialogFragment?
        prevDialogProgressFragment?.dismiss()
    }
}