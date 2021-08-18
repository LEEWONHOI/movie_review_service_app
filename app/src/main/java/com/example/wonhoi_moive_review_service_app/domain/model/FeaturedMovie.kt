package com.example.wonhoi_moive_review_service_app.domain.model

data class FeaturedMovie(
    val movie : Movie,
    val latestReview : Review?
)