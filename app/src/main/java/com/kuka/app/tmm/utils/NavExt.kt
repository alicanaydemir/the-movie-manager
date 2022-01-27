package com.kuka.app.tmm.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.kuka.app.tmm.R
import com.kuka.app.tmm.databinding.ActivityMainBinding

fun setVisibilityBottom(destinationId: Int, binding: ActivityMainBinding) {

    when (destinationId) {
        R.id.loginFragment -> {
            hideView(binding.bottomNavigationView)
        }

        R.id.movieDetailFragment -> {
            hideView(binding.bottomNavigationView)
        }

        else -> {
            showView(binding.bottomNavigationView)
        }
    }
}

private fun hideView(view: View?) {
    view?.animate()?.alpha(0f)
        ?.setDuration(150)
        ?.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                view.visibility = View.GONE
            }
        })
}

private fun showView(view: View?) {
    view?.animate()?.alpha(1f)
        ?.setDuration(150)
        ?.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                view.visibility = View.VISIBLE
            }
        })
}