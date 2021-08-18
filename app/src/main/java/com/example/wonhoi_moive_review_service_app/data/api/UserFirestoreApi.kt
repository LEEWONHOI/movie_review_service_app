package com.example.wonhoi_moive_review_service_app.data.api

import com.example.wonhoi_moive_review_service_app.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserFirestoreApi(
    private val firestore : FirebaseFirestore
) : UserApi {

    override suspend fun saveUser(user: User): User =
        firestore.collection("users")
            .add(user)
            .await()
            .let {
                User(it.id)
            }
}