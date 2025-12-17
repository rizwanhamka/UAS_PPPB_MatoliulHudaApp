package com.example.matoliulhudaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.example.matoliulhudaapp.components.*

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        // ✅ Splash Screen (WAJIB sebelum super.onCreate)
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_notification -> loadFragment(NewsFragment())
                R.id.nav_input -> loadFragment(InputFragment())
                R.id.nav_profile -> {
                    if (auth.currentUser != null) {
                        loadFragment(ProfileFragment())
                    } else {
                        loadFragment(LoginFragment())
                    }
                }
                else -> loadFragment(HomeFragment())
            }
            true
        }

        // ✅ Default fragment (hanya saat pertama kali)
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
