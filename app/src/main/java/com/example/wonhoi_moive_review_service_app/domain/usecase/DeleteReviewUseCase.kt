package com.example.wonhoi_moive_review_service_app.domain.usecase

import com.example.wonhoi_moive_review_service_app.data.repository.ReviewRepository
import com.example.wonhoi_moive_review_service_app.domain.model.Review

class DeleteReviewUseCase(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(review: Review) =
        reviewRepository.removeReview(review)
}