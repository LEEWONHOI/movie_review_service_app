package com.example.wonhoi_moive_review_service_app.domain.model

data class MovieReviews(
    val myReview: Review?,
    val othersReview: List<Review>
)