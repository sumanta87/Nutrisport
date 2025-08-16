package com.nutrisport.data.domain

import com.nutrisport.shared.Customer
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore

class CustomerRepositoryImpl : CustomerRepository {
    override fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    override suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            if (user != null) {
                val customerCollection = Firebase.firestore.collection(collectionPath = "customer")
                val customer = Customer(
                    id = user.uid,
                    firstName = user.displayName?.split(" ")?.firstOrNull() ?: "Unknow",
                    lastname = user.displayName?.split(" ")?.lastOrNull() ?: "unknown",
                    email = user.email ?: "Unknown"

                )
                val customerExist = customerCollection.document(user.uid).get().exists
                if (customerExist) {
                    onSuccess()
                } else {
                    customerCollection.document(user.uid).set(customer)
                    onSuccess()
                }

            } else {
                onError("User is not available:")
            }

        } catch (e: Exception) {
            onError("Error while creating a customer:${e.message}")
        }
    }
}