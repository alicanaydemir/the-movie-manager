package com.kuka.app.tmm.ui.main

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kuka.app.tmm.R
import com.kuka.app.tmm.core.BaseActivity
import com.kuka.app.tmm.databinding.ActivityMainBinding
import com.kuka.app.tmm.utils.extensions.restartApp
import com.kuka.app.tmm.utils.extensions.setVisibilityBottom
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun prepareView(savedInstanceState: Bundle?) {

        addInitialDataListener()
        initObserver()

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.container
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            setVisibilityBottom(binding = binding, destinationId = destination.id)
        }

    }

    private fun initObserver() {
        viewModel.restartApp.observe(this) {
            if (it) restartApp<MainActivity>()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun setSplash() {
        installSplashScreen()
    }

    private fun addInitialDataListener() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    return if (!viewModel.isLoadingSplash.value) {
                        // The content is ready; start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready; suspend.
                        false
                    }
                }
            }
        )
    }

}