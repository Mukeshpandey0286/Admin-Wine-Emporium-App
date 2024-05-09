package com.example.adminflavorfiesta.Models

data class UserModel(
    val name : String ?= null,
    val nameOfRestro : String ?= null,
    val email : String ?= null,
    val password : String ?= null
)
