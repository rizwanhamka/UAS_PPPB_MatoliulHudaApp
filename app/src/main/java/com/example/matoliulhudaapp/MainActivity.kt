package com.example.matoliulhudaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.matoliulhudaapp.components.NotificationsFragment
import com.example.matoliulhudaapp.components.ProfileFragment
import com.example.matoliulhudaapp.components.InputFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_notification -> loadFragment(NotificationsFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
                R.id.nav_input -> loadFragment(InputFragment())
                else -> loadFragment(HomeFragment())
            }
            true
        }

        loadFragment(HomeFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
