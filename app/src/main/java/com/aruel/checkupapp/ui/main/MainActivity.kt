package com.aruel.checkupapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.aruel.checkupapp.ui.intro.IntroActivity
import com.aruel.checkupapp.R
import com.aruel.checkupapp.data.factory.ViewModelFactory
import com.aruel.checkupapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                logoutAndRedirect()
            }
        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        supportActionBar?.hide()

        val navigateTo = intent.getIntExtra("navigate_to", -1)
        if (navigateTo != -1) {
            navController.navigate(navigateTo)
        }
    }


    private fun logoutAndRedirect() {
        startActivity(Intent(this, IntroActivity::class.java))
        finish()
    }



}