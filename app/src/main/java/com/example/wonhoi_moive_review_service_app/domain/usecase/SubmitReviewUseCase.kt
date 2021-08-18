package com.example.wonhoi_moive_review_service_app.domain.usecase

import com.example.wonhoi_moive_review_service_app.data.repository.ReviewRepository
import com.example.wonhoi_moive_review_service_app.data.repository.UserRepository
import com.example.wonhoi_moive_review_service_app.domain.model.Movie
import com.example.wonhoi_moive_review_service_app.domain.model.Review
import com.example.wonhoi_moive_review_service_app.domain.model.User

class SubmitReviewUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(
        movie: Movie,
        content: String,
        score: Float
    ): Review {
        var user = userRepository.getUser()

        if (user == null) {
            userRepository.saveUser(User())
            user = userRepository.getUser()
        }

        return reviewRepository.addReview(
            Review(
                userId = user!!.id,
                movieId = movie.id,
                content = content,
                score = score
            )
        )
    }
}