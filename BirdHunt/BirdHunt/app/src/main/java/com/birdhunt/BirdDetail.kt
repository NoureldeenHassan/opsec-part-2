package com.birdhunt

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.birdhunt.databinding.ActivityBirdDetailBinding // Import your View Binding class
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.Map

class BirdDetail : AppCompatActivity() {
    private lateinit var binding: ActivityBirdDetailBinding // Declare View Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflate the layout using View Binding
        binding = ActivityBirdDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Handle switch listeners
        binding.switchSeen.setOnCheckedChangeListener { _, isChecked ->
            updateBirdSeenStatus(isChecked)
        }

        binding.switchFavourite.setOnCheckedChangeListener { _, isChecked ->
            updateBirdFavouriteStatus(isChecked)
        }

        // Set up bottom navigation
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

        // Load bird details from Firebase
        loadBirdDetails()
    }

    private fun updateBirdSeenStatus(isSeen: Boolean) {
        // Implement your logic to update the bird's seen status
    }

    private fun updateBirdFavouriteStatus(isFavourite: Boolean) {
        // Implement your logic to update the bird's favourite status
    }

    private fun loadBirdDetails() {
        // Implement your logic to load bird details from Firebase
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("birds")
        database.child("birdId") // Replace with actual bird ID
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Retrieve and display bird data
                        val birdName = snapshot.child("name").value.toString()
                        val birdDescription = snapshot.child("description").value.toString()
                        val imageUrl = snapshot.child("image").value.toString()

                        binding.textViewBirdDescription.text = birdDescription
                        binding.textViewAddBirdName.text = birdName
                        // Load image using your preferred image loading library (e.g., Glide, Picasso)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error
                }
            })
    }
}
