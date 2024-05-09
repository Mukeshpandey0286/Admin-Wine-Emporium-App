package com.example.adminflavorfiesta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminflavorfiesta.Adapters.ItemsAdapter
import com.example.adminflavorfiesta.dataClass.AllItems
import com.example.adminflavorfiesta.databinding.ActivityAllItemsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemsActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var menuItems : ArrayList<AllItems> = ArrayList()
    private lateinit var binding : ActivityAllItemsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllItemsBinding.inflate(layoutInflater)

//        initialize database instance
        databaseReference = FirebaseDatabase.getInstance().reference
        setupUI()
        retrieveMenuItem()


        binding.backButton.setOnClickListener {
            finish()
        }

        setContentView(binding.root)
    }


    private fun setupUI() {
        // Set up a ProgressBar in XML layout and reference it here
        binding.progressBar.visibility = View.VISIBLE
        // Add more setup code if needed
    }

    private fun hideLoading() {
        // Hide the ProgressBar when data is fetched
        binding.progressBar.visibility = View.GONE
        // Add more cleanup code if needed
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef : DatabaseReference = database.reference.child("menu")

//        fetch data from database
        foodRef.addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                clear existing data before populating
                menuItems.clear()

//                loop for through food item
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(AllItems::class.java)
                    menuItem?.let {
                        Log.d("FoodItem", "foodImage: ${it.foodImage}")
                        menuItems.add(it)
                    }
                }
                setAdapter()
                hideLoading()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: failed")
                hideLoading()
            }
        })
    }

    private fun setAdapter() {
        val adapter = ItemsAdapter(this@AllItemsActivity,menuItems, databaseReference)
        binding.menuRCV.layoutManager = LinearLayoutManager(this)
        binding.menuRCV.adapter = adapter
    }
}