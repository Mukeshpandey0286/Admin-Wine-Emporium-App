package com.example.adminflavorfiesta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.adminflavorfiesta.Models.UserModel
import com.example.adminflavorfiesta.databinding.ActivitySignupBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class SignUpActivity : AppCompatActivity() {

    private lateinit var userName: String
    private lateinit var email: String
    private lateinit var nameOfRestorant: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize Firebase Auth and Database
        auth = Firebase.auth
        database = Firebase.database.reference

        binding.signupBtn.setOnClickListener {
            // Getting all details and storing them in variables
            userName = binding.nameOwner.text.toString().trim()
            email = binding.email.text.toString().trim()
            nameOfRestorant = binding.restroName.text.toString().trim()
            password = binding.pass.text.toString().trim()

            // Check for a valid email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check for an existing email before attempting to create an account
            database.child("user").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            // Email already exists
                            Toast.makeText(
                                this@SignUpActivity,
                                "Email Already Exists!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // Email is unique, proceed with account creation
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            this@SignUpActivity,
                                            "Account Created Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        saveUserData()
                                        val intent =
                                            Intent(this@SignUpActivity, LoginActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this@SignUpActivity,
                                            "Account Creation Failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.d("Account", "createAccount : Failure", task.exception)
                                    }
                                }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Database error: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        // Showing a list of locations with the help of an adapter
        val locationList = arrayOf("Bageshwar", "Almora", "Nainital", "Champawat")
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, locationList)
        val autoCompleteTxtView = binding.listOfLoc
        autoCompleteTxtView.setAdapter(adapter)

        binding.alredyAcc.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // Save data in the database
    private fun saveUserData() {
        // Getting all details and storing them in variables
        userName = binding.nameOwner.text.toString().trim()
        email = binding.email.text.toString().trim()
        nameOfRestorant = binding.restroName.text.toString().trim()
        password = binding.pass.text.toString().trim()

        val user = UserModel(userName, nameOfRestorant, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        // Save data in the Firebase database
        database.child("user").child(userId).setValue(user)
    }
}
