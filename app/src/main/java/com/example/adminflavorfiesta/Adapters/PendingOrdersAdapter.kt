package com.example.adminflavorfiesta.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminflavorfiesta.databinding.PendingOrderRcvBinding

class PendingOrdersAdapter(private val CustomerDetail : ArrayList<String>, private val OrderedDetail : ArrayList<String>, private val OrderedImg : ArrayList<Int>, private val context : Context) : RecyclerView.Adapter<PendingOrdersAdapter.PendingOrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrdersViewHolder {
        val binding = PendingOrderRcvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrdersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return CustomerDetail.size
    }

    override fun onBindViewHolder(holder: PendingOrdersViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class PendingOrdersViewHolder(private val binding : PendingOrderRcvBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                orderedCustomerName.text = CustomerDetail[position]
                QuantityAmount.text = OrderedDetail[position]
                orderedFoodmage.setImageResource(OrderedImg[position])

                orderAcceptBtn.apply {
                    if (!isAccepted){
                        text = "Accept"
                    }else{
                        text = "Dispatch"
                    }

                    setOnClickListener {
                        if (!isAccepted){
                            text = "Dispatch"
                            isAccepted = true
                            showToast("Order is Accepted")
                        }
                        else{
                            CustomerDetail.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order is Dispatched")
                        }
                    }
                }

            }
        }

    }

    private fun showToast(message : String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}