package com.aksampazari.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.aksampazari.R
import com.aksampazari.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btmNav = binding.btmNav
        val navController = Navigation.findNavController(this, R.id.fragment_container_view)
        NavigationUI.setupWithNavController(btmNav, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment,
                R.id.loginFragment,
                R.id.registerFragment -> {
                    btmNav.visibility = View.GONE
                }
                else -> {
                    btmNav.visibility = View.VISIBLE
                }
            }
        }
    }
}