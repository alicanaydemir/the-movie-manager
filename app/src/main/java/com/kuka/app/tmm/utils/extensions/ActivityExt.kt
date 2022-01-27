package com.kuka.app.tmm.utils.extensions

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.kuka.app.tmm.ui.progress.ProgressDialogFragment

fun AppCompatActivity.showDialogProgress(boolean: Boolean) {
    if (boolean) {
        val dialogProgressFragment = ProgressDialogFragment()
        dialogProgressFragment.show(supportFragmentManager, "dialogProgress2")
    } else {
        val prevDialogProgressFragment =
            supportFragmentManager.findFragmentByTag("dialogProgress2") as ProgressDialogFragment?
        prevDialogProgressFragment?.dismiss()
    }
}

inline fun <reified T : Activity> Activity.restartApp() {
    startActivity(Intent(this, T::class.java))
    finishAffinity()
}