package com.example.adminflavorfiesta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminflavorfiesta.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private val binding : ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var isEnabled = false
        binding.apply {
            name.isEnabled = false
            address.isEnabled = false
            phone.isEnabled = false
            password.isEnabled = false
            email.isEnabled = false

            editableTextBtn.setOnClickListener {
                isEnabled = ! isEnabled
                name.isEnabled = isEnabled
                address.isEnabled = isEnabled
                phone.isEnabled = isEnabled
                password.isEnabled = isEnabled
                email.isEnabled = isEnabled

                if (isEnabled){
                    name.requestFocus()
                }
            }

            backButton.setOnClickListener {
                finish()
            }
        }
    }
}