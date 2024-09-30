package com.birdhunt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChangeDetails : AppCompatActivity() {

    // Firebase Auth instance
    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser

        // UI elements
        val textUsernameCP = findViewById<TextView>(R.id.editTextNUsername)
        val textCurrentPassword = findViewById<EditText>(R.id.editTextCPassword)
        val textNewPassword = findViewById<EditText>(R.id.editTextNCPassword)
        val btnChangeP = findViewById<Button>(R.id.btnSaveDetails)

        // Display current email (which acts as the username)
        textUsernameCP.text = currentUser?.email

        btnChangeP.setOnClickListener {
            val newPassword = textNewPassword.text.toString()
            val currentPassword = textCurrentPassword.text.toString()
            val newEmail = textUsernameCP.text.toString()

            if (newPassword.isEmpty() && newEmail == currentUser?.email) {
                Toast.makeText(this, "No changes detected", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Reauthenticate before making sensitive changes
            reauthenticateAndUpdateDetails(currentPassword, newEmail, newPassword)
        }
    }

    /**
     * Reauthenticate the user with their current password, then update email and/or password.
     */
    private fun reauthenticateAndUpdateDetails(currentPassword: String, newEmail: String, newPassword: String) {
        val email = currentUser?.email ?: return

        // Reauthenticate the user with current password
        val credential = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, currentPassword)

        credential.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Reauthentication successful, update email and/or password
                if (newEmail != email) {
                    // Update email
                    currentUser?.updateEmail(newEmail)?.addOnCompleteListener { updateEmailTask ->
                        if (updateEmailTask.isSuccessful) {
                            Toast.makeText(this, "Email updated successfully", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Error updating email: ${updateEmailTask.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                if (newPassword.isNotEmpty()) {
                    // Update password
                    currentUser?.updatePassword(newPassword)?.addOnCompleteListener { updatePasswordTask ->
                        if (updatePasswordTask.isSuccessful) {
                            Toast.makeText(this, "Password updated successfully", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Error updating password: ${updatePasswordTask.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                // Redirect back to MainActivity after changes
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Reauthentication failed
                Toast.makeText(this, "Reauthentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
