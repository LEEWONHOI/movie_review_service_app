package com.example.wonhoi_moive_review_service_app.domain.usecase

import com.example.wonhoi_moive_review_service_app.data.repository.ReviewRepository
import com.example.wonhoi_moive_review_service_app.data.repository.UserRepository
import com.example.wonhoi_moive_review_service_app.domain.model.MovieReviews
import com.example.wonhoi_moive_review_service_app.domain.model.Review
import com.example.wonhoi_moive_review_service_app.domain.model.User

class GetAllMovieReviewsUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository : ReviewRepository
    ) {

    suspend operator fun invoke(movieId : String) : MovieReviews {
        val reviews = reviewRepository.getAllMovieReviews(movieId)
        val user = userRepository.getUser()

        if(user == null) {
            userRepository.saveUser(User())

            return MovieReviews(null,reviews)
        }

        return MovieReviews(
            // 내 리뷰
            reviews.find {
                it.userId == user.id
            },
            // 다른 사람 리뷰
            reviews.filter {
                it.userId != user.id
            }
        )
    }
}