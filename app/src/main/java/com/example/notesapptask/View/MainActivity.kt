package com.example.notesapptask.View

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.example.notesapptask.R
import com.example.notesapptask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isUserSignedIn()) {
            // User is signed in, show the main content fragment
            replaceFragment(HomeFragment())
        } else {
            // User is not signed in, show the sign-in fragment
            replaceFragment(SignInFragment())
        }

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars = true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag, fragment)
            .commit()
    }

    private fun Context.isUserSignedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_signed_in", false)
    }
}
