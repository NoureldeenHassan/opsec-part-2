package com.birdhunt

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddBird : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bird)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // UI elements
        val editTextBirdName = findViewById<EditText>(R.id.editTextBirdName)
        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)
        val editTextLocation = findViewById<EditText>(R.id.editTextLocation)
        val editTextURL = findViewById<EditText>(R.id.editTextURL)
        val btnAddBird = findViewById<Button>(R.id.btnUserAddBird)

        // Handle adding bird data
        btnAddBird.setOnClickListener {
            val birdName = editTextBirdName.text.toString().trim()
            val birdDescription = editTextDescription.text.toString().trim()
            val birdLocation = editTextLocation.text.toString().trim()
            val birdURL = editTextURL.text.toString().trim()

            if (birdName.isNotEmpty() && birdDescription.isNotEmpty() && birdLocation.isNotEmpty() && birdURL.isNotEmpty()) {
                // Create a new Bird object
                val bird = Bird(birdName, birdDescription, birdLocation, birdURL)

                // Add a new document with a generated ID
                db.collection("birds")
                    .add(bird)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        Toast.makeText(this, "Bird added successfully", Toast.LENGTH_SHORT).show()
                        clearFields()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                        Toast.makeText(this, "Error adding bird", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to clear input fields
    private fun clearFields() {
        findViewById<EditText>(R.id.editTextBirdName).text.clear()
        findViewById<EditText>(R.id.editTextDescription).text.clear()
        findViewById<EditText>(R.id.editTextLocation).text.clear()
        findViewById<EditText>(R.id.editTextURL).text.clear()
    }

    companion object {
        private const val TAG = "AddBirdActivity"
    }
}
