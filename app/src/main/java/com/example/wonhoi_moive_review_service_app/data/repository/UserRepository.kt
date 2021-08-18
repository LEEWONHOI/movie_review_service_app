package com.example.wonhoi_moive_review_service_app.data.repository

import com.example.wonhoi_moive_review_service_app.domain.model.User


interface UserRepository {

    suspend fun getUser() : User?

    suspend fun saveUser(user: User)

}