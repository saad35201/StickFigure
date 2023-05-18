package com.saadi.stickfigure.feature_auth.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.saadi.stickfigure.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAuthBase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent?.let {
            val destination = it.getIntExtra("destinationFragment", R.id.fragmentSplash)
            // Get the NavHostFragment
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val graphInflater = navController.navInflater
            val navGraph = graphInflater.inflate(R.navigation.auth_nav_graph)

            // Set the desired fragment as the start destination
            navGraph.setStartDestination(destination)

            // Set the modified graph to the navController
            navController.graph = navGraph

        }

    }
}