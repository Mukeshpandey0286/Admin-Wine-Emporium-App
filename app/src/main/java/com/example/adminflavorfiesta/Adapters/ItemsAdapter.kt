package com.example.adminflavorfiesta.Adapters

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.adminflavorfiesta.R
import com.example.adminflavorfiesta.dataClass.AllItems
import com.example.adminflavorfiesta.databinding.AllItemRcvBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage

class ItemsAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllItems>,
    databaseReference: DatabaseReference
) : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {

    private val itemQuantities = IntArray(menuList.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding = AllItemRcvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.bind(position)
    }

  inner class ItemsViewHolder(private val binding: AllItemRcvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = menuList[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)
                Log.d("image", "foodimage : ${uri} ${uriString}")
                menuName.text = menuItem.foodName
                RestaurantName.text = menuItem.restroName
                menuPrice.text = menuItem.foodPrice
                // Load image directly from Firebase Storage URL using Glide
                Glide.with(context)
                    .load(menuItem.foodImage)  // Use the foodImage field as the URL
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(menuImage)

                itemsNo.text = itemQuantities[position].toString()
                minusIcon.setOnClickListener {
                    decreaseItems(position)
                }

                plusIcon.setOnClickListener {
                    increaseItem(position)
                }

                removeBtn.setOnClickListener {
                    removeItem(position)
                }

            }
        }

      private fun increaseItem(position: Int) {
        if (itemQuantities[position] < 10){
            itemQuantities[position]++
            binding.itemsNo.text = itemQuantities[position].toString()
        }
      }

      private fun decreaseItems(position: Int) {
          if(itemQuantities[position] > 1){
              itemQuantities[position]--
              binding.itemsNo.text  = itemQuantities[position].toString()
          }
      }

      private fun removeItem(position: Int) {
          menuList.removeAt(position)
          notifyItemRemoved(position)
          notifyItemRangeChanged(position, itemCount)
      }

  }
}
