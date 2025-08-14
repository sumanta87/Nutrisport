package com.nutrisport.shared

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: String,
    val firstName: String,
    val lastname: String,
    val email: String,
    val city: String?= null,
    val postalcode:Int?=null,
    val address: String?=null,
    val phNumber: PhoneNumber? = null,
    val cart: List<CartItem> = emptyList()
)
@Serializable
data class PhoneNumber(
    val dailcode:Int,
    val number: String
)