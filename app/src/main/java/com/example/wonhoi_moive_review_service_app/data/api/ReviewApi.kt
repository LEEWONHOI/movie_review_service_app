package com.example.wonhoi_moive_review_service_app.data.api

import com.example.wonhoi_moive_review_service_app.domain.model.Review

interface ReviewApi {

    suspend fun getLatestReview(movieId : String) : Review?

    suspend fun getAllMovieReviews(movieId: String) : List<Review>

    suspend fun getAllUserReviews(userId : String) : List<Review>

    suspend fun addReview(review : Review) : Review

    suspend fun removeReview(review : Review)

}