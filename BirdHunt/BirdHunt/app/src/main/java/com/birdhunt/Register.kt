package com.birdhunt

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Set up window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        val textUsernameSignUp = findViewById<EditText>(R.id.editTextCUsername)
        val textCrtPassword = findViewById<EditText>(R.id.editTextCPassword)
        val textConPassword = findViewById<EditText>(R.id.editTextCCPassword)
        val registerUser = findViewById<Button>(R.id.btnUserRegister)
        val btnLogin = findViewById<TextView>(R.id.textViewDHAAlink)

        // Handle user registration
        registerUser.setOnClickListener {
            val email = textUsernameSignUp.text.toString()
            val password = textCrtPassword.text.toString()
            val confirmPassword = textConPassword.text.toString()

            if (password == confirmPassword) {
                // Register the user with Firebase
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registration success
                            Log.d(TAG, "createUserWithEmail:success")
                            Toast.makeText(this, "User is Registered", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            // If registration fails, display a message to the user
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show()
                textConPassword.setText("")
            }
        }

        // Navigate back to login screen
        btnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
