package com.example.adminflavorfiesta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminflavorfiesta.Adapters.DeliveryDetailsAdapter
import com.example.adminflavorfiesta.databinding.ActivityOutForDeiveryBinding

class OutForDeiveryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOutForDeiveryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutForDeiveryBinding.inflate(layoutInflater)

        val customerName = arrayListOf("Mukesh", "Krishna", "Amit", "Rahul", "Nitin")
        val paymentStatus = arrayListOf("Received", "Received", "Not Received", "Pending","Not Received")
        val adapter = DeliveryDetailsAdapter(customerName, paymentStatus)
        binding.deliveryItemsRcv.layoutManager = LinearLayoutManager(this)
        binding.deliveryItemsRcv.adapter = adapter

        binding.backButton.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
    }
}