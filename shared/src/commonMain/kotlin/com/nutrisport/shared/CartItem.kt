package com.nutrisport.shared

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
@Serializable
@OptIn(ExperimentalUuidApi::class)
data class CartItem(
    val id: String = Uuid.random().toHexString(),
    val proudctId: String,
    val flavor: String? = null,
    val quantity: Int
)
