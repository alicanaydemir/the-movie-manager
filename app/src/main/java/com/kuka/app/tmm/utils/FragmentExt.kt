package com.kuka.app.tmm.utils

import androidx.fragment.app.Fragment
import com.kuka.app.tmm.ui.progress.ProgressDialogFragment

fun Fragment.showDialogProgress(boolean: Boolean) {
    if (boolean) {
        val dialogProgressFragment = ProgressDialogFragment()
        dialogProgressFragment.show(requireActivity().supportFragmentManager, "dialogProgress")
    } else {
        val prevDialogProgressFragment =
            requireActivity().supportFragmentManager.findFragmentByTag("dialogProgress") as ProgressDialogFragment?
        prevDialogProgressFragment?.dismiss()
    }
}