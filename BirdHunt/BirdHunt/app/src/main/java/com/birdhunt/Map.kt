package com.birdhunt

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.birdhunt.databinding.ActivityMapBinding
import com.birdhunt.databinding.ActivityProfileBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlin.collections.Map

class Map : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment

        mapFragment?.getMapAsync(this)
    }



    override fun onMapReady(map: GoogleMap) {
        val latLng = LatLng(33.9137 , 25.5827)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,19f))
    }
}

