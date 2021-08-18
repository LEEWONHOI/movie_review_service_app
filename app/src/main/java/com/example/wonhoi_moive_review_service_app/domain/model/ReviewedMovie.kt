package com.example.wonhoi_moive_review_service_app.domain.model

data class ReviewedMovie(
    val movie: Movie,
    val myReview: Review
)