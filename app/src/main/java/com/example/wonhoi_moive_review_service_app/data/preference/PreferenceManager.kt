package com.example.wonhoi_moive_review_service_app.data.preference

interface PreferenceManager {

    fun getString(key: String): String?

    fun putString(key: String, value: String)

}