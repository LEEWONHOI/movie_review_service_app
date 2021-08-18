package com.example.wonhoi_moive_review_service_app.domain.usecase

import com.example.wonhoi_moive_review_service_app.data.repository.MovieRepository
import com.example.wonhoi_moive_review_service_app.data.repository.ReviewRepository
import com.example.wonhoi_moive_review_service_app.domain.model.FeaturedMovie

class GetRandomFeaturedMovieUseCase(
    private val movieRepository : MovieRepository,
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke() : FeaturedMovie? {
        val featureMovies = movieRepository.getAllMovies()
            .filter {
                it.id.isNullOrBlank().not() // .isNullOrBlank().not() 아이디가 null 인건 제외
            }
            .filter {
                it.isFeatured == true       // 추쳔 영화가 true 인거
            }

        if (featureMovies.isNullOrEmpty()) {
            return null
        }

        return featureMovies.random()
            .let { movie ->
                val latestReview = reviewRepository.getLatestReview(movie.id!!)
                FeaturedMovie(movie, latestReview)
            }
    }

}