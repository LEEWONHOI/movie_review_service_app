package com.example.wonhoi_moive_review_service_app.data.api

import com.example.wonhoi_moive_review_service_app.domain.model.User

interface UserApi {

    suspend fun saveUser(user: User): User

}