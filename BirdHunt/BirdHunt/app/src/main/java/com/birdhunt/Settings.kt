package com.birdhunt

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.birdhunt.databinding.ActivityMainBinding
import com.birdhunt.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnChangeDetails = findViewById<TextView>(R.id.btnChangeDetails)

        btnChangeDetails.setOnClickListener{
            val intentCustomerSwitch = Intent(this, ChangeDetails::class.java)
            startActivity(intentCustomerSwitch)
        }

        val btnlogOff = findViewById<TextView>(R.id.btnUserLogoff)

        btnlogOff.setOnClickListener{
            val intentCustomerSwitch = Intent(this, MainActivity::class.java)
            startActivity(intentCustomerSwitch)
        }

        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)

        // Check the saved theme and apply it
        val isDarkMode = sharedPreferences.getBoolean("darkMode", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.switchScreenMode.isChecked = true
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.switchScreenMode.isChecked = false
        }

        // Set up the switch listener for toggling dark mode
        binding.switchScreenMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Enable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveThemePreference(true)  // Correct usage
            } else {
                // Enable light mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveThemePreference(false) // Correct usage
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.profileNav -> {
                    // Start the ProfileActivity
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                }
                R.id.mapNav -> {
                    // Start the MapActivity
                    val intent = Intent(this, Map::class.java)
                    startActivity(intent)
                }
                R.id.birdListNav -> {
                    // Start the BirdListActivity
                    val intent = Intent(this, BirdList::class.java)
                    startActivity(intent)
                }
                R.id.settingsNav -> {
                    // Start the SettingsActivity
                    val intent = Intent(this, Settings::class.java)
                    startActivity(intent)
                }
                else -> {}
            }
            true
        }
    }
    private fun saveThemePreference(isDarkMode: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("darkMode", isDarkMode)
        editor.apply() // Save the preference
    }
}