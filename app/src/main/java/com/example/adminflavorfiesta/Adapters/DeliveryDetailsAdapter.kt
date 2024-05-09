package com.example.adminflavorfiesta.Adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminflavorfiesta.databinding.OutForDeliveryRcvBinding

class DeliveryDetailsAdapter(private val CustomerDetail : ArrayList<String>, private val PaymentStatus: ArrayList<String>) : RecyclerView.Adapter<DeliveryDetailsAdapter.DeliveryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = OutForDeliveryRcvBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return DeliveryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return CustomerDetail.size
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class DeliveryViewHolder(private val binding : OutForDeliveryRcvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                customerName.text = CustomerDetail[position]
                paymentStatus.text = PaymentStatus[position]

                val colorMap = mapOf(
                    "Received" to Color.GREEN, "Not Received" to Color.RED, "Pending" to Color.GRAY
                )
                paymentStatus.setTextColor(colorMap[PaymentStatus[position]]?:Color.BLACK)
                statusColor.backgroundTintList= ColorStateList.valueOf(colorMap[PaymentStatus[position]]?:Color.BLACK)

            }
        }

    }
}