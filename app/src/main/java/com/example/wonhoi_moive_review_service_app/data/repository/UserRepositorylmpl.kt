package com.example.wonhoi_moive_review_service_app.data.repository

import com.example.wonhoi_moive_review_service_app.data.api.UserApi
import com.example.wonhoi_moive_review_service_app.data.preference.PreferenceManager
import com.example.wonhoi_moive_review_service_app.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepositorylmpl(
    private val userApi : UserApi,
    private val preferenceManager : PreferenceManager,
    private val dispatchers : CoroutineDispatcher
) : UserRepository {

    override suspend fun getUser(): User? = withContext(dispatchers) {
        preferenceManager.getString(KEY_USER_ID)?.let {
            User(it)
        }
    }

    override suspend fun saveUser(user: User) = withContext(dispatchers) {
        val newUser = userApi.saveUser(user)
        preferenceManager.putString(KEY_USER_ID, newUser.id!!)
    }

    companion object {
        private const val KEY_USER_ID = "KEY_USER_ID"
    }

}