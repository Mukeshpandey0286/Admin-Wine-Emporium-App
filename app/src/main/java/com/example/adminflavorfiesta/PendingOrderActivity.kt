package com.example.adminflavorfiesta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminflavorfiesta.Adapters.PendingOrdersAdapter
import com.example.adminflavorfiesta.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPendingOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)

        val orderCustomerName = arrayListOf("mukesh", "amit", "krishna", "kamal")
        val orderQuantity = arrayListOf("4","5","2","9")
        val orderFoodImg = arrayListOf(R.drawable.pizzabite, R.drawable.paneer, R.drawable.jalebi, R.drawable.rasgulla)
        val adapter = PendingOrdersAdapter(orderCustomerName,orderQuantity,orderFoodImg, this)
        binding.PendingOrderRcv.layoutManager = LinearLayoutManager(this)
        binding.PendingOrderRcv.adapter = adapter

        binding.backButton.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
    }
}