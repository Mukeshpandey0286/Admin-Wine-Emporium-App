package com.example.adminflavorfiesta

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminflavorfiesta.dataClass.AllItems
import com.example.adminflavorfiesta.databinding.ActivityAddItemsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemsActivity : AppCompatActivity() {
    private val binding : ActivityAddItemsBinding by lazy {
         ActivityAddItemsBinding.inflate(layoutInflater)
    }

//    all food related vars
 private lateinit var foodName : String
    private lateinit var foodPrice : String
    private lateinit var foodDescription : String
    private var foodImage : Uri ?= null
    private lateinit var foodIngrediant : String
 private lateinit var restroName : String
    private lateinit var auth : FirebaseAuth
    private lateinit var databse : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        firebase And database connection
        auth = FirebaseAuth.getInstance()
        databse = FirebaseDatabase.getInstance()

        binding.addItemBtn.setOnClickListener {
//            get all input details
            foodName = binding.itemName.text.toString().trim()
            restroName = binding.restoName.text.toString().trim()
            foodPrice = binding.itemPrice.text.toString().trim()
            foodDescription = binding.editTextDesc.text.toString().trim()
            foodIngrediant = binding.editTextIngrediants.text.toString().trim()

            if (!(foodName.isBlank() || restroName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngrediant.isBlank())) {
                // Check if foodPrice contains only numeric values
                if (isNumeric(foodPrice)) {
                    // Additional check to ensure foodPrice is not negative
                    if (foodPrice.toDouble() >= 0) {
                        // Show loading animation
                        showLoading(true)
                        uploadData()
                        Toast.makeText(this, "Item adding! Please wait!🤷‍♂️", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(
                            this,
                            "Wine price cannot be a negative value🤷‍♂️",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this, "Wine price should be a numeric value🤦‍♂️", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Fill all the Details😒", Toast.LENGTH_SHORT).show()
            }
        }
//        for image selection code...
        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

//        for arrow back button
        binding.backButton.setOnClickListener {
            finish()
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnText.text = "" // Clear text
            binding.addItemBtn.isEnabled = false // Disable the button
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnText.text = "Add Item" // Restore button text
            binding.addItemBtn.isEnabled = true // Enable the button again
        }
    }


    private fun uploadData() {
//        get reference to the "menu" node in the database
        val menuRef = databse.getReference("menu")

//        Generate a unique key for the new menu item
        val newItemKey = menuRef.push().key

        if (foodImage != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/$newItemKey.jpg")
            val uploadTask = imageRef.putFile(foodImage!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnCompleteListener { downloadUri ->
                    val newItem = AllItems(
                        foodName = foodName,
                        foodPrice = "₹$foodPrice",
                        foodDescription = foodDescription,
                        foodImage = downloadUri.result.toString(),
                        foodIngrediant = foodIngrediant,
                        restroName = restroName
                    )
                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem).addOnCompleteListener {
                            Toast.makeText(this, "Data uploaded successfully😁", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        } .addOnFailureListener {
                            Toast.makeText(this, "Failed to upload data😥", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
                .addOnFailureListener {
                    Toast.makeText(this, "Upload failed🤷‍♂️", Toast.LENGTH_SHORT).show()
                }

        }else{
            Toast.makeText(this, "Please select an image(❁´◡`❁)", Toast.LENGTH_SHORT).show()

        }
    }

    //        for image selection code...
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri ->
        if (uri != null){
            binding.selectedImage.setImageURI(uri)
            foodImage = uri
        }
    }
}

    private fun isNumeric(input: String): Boolean {
        return input.matches("-?\\d+(\\.\\d+)?".toRegex())
    }

