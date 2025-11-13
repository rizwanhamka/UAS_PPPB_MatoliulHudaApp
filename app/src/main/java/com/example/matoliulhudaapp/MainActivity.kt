package com.example.matoliulhudaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.matoliulhudaapp.components.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_notification -> loadFragment(NotificationsFragment())
                R.id.nav_input -> loadFragment(InputFragment())
                R.id.nav_profile -> {
                    if (auth.currentUser != null) {
                        // ✅ Sudah login → tampilkan profil
                        loadFragment(ProfileFragment())
                    } else {
                        // ❌ Belum login → tampilkan halaman login
                        loadFragment(LoginFragment())
                    }
                }
                else -> loadFragment(HomeFragment())
            }
            true
        }

        // Halaman default saat app dibuka
        loadFragment(HomeFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
