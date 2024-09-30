package com.birdhunt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.birdhunt.databinding.ActivityBirdListBinding

class BirdList : AppCompatActivity() {

    private lateinit var binding: ActivityBirdListBinding
    private lateinit var birdAdapter: BirdAdapter
    private lateinit var birdList: List<Bird>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBirdListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnAddnewBird = findViewById<Button>(R.id.btnAddBird)

        btnAddnewBird.setOnClickListener {
            val intentAddbird = Intent(this, AddBird::class.java)
            startActivity(intentAddbird)
        }


        // Sample bird data
        birdList = listOf(
            Bird("Sparrow", "A small, brown bird", "Urban areas", "url_to_image"),
            Bird("Eagle", "A large bird of prey", "Mountains", "url_to_image"),
            // Add more bird data here
        )

        // Setup RecyclerView
        val recyclerView: RecyclerView = binding.recyclerViewBirdList // Ensure you have this in your layout
        recyclerView.layoutManager = LinearLayoutManager(this)
        birdAdapter = BirdAdapter(birdList)
        recyclerView.adapter = birdAdapter

        // Setup Bottom Navigation View
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profileNav -> {
                    // Start the ProfileActivity
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                    true
                }
                R.id.mapNav -> {
                    // Start the MapActivity
                    val intent = Intent(this, Map::class.java)
                    startActivity(intent)
                    true
                }
                R.id.birdListNav -> {
                    // Current activity, no action needed
                    true
                }
                R.id.settingsNav -> {
                    // Start the SettingsActivity
                    val intent = Intent(this, Settings::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }


}
