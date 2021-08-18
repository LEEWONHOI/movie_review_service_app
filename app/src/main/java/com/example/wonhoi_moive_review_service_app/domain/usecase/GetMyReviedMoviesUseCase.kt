package com.example.wonhoi_moive_review_service_app.domain.usecase

import com.example.wonhoi_moive_review_service_app.data.repository.MovieRepository
import com.example.wonhoi_moive_review_service_app.data.repository.ReviewRepository
import com.example.wonhoi_moive_review_service_app.data.repository.UserRepository
import com.example.wonhoi_moive_review_service_app.domain.model.ReviewedMovie
import com.example.wonhoi_moive_review_service_app.domain.model.User

class GetMyReviedMoviesUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(): List<ReviewedMovie> {
        val user = userRepository.getUser()

        if (user == null) {
            userRepository.saveUser(User())
            return emptyList()
        }

        val reviews = reviewRepository.getAllUserReviews(user.id!!)
            .filter {
                it.movieId.isNullOrBlank().not()
            }
        if (reviews.isNullOrEmpty()) {
            return emptyList()
        }


        return movieRepository
            .getMovies(reviews.map { review ->
                review.movieId!!
            })
            .mapNotNull { movie ->
                val relateReview = reviews.find {
                    it.movieId == movie.id
                }
                relateReview?.let {
                    ReviewedMovie(movie, it)
                }
            }
    }

}