package com.example.wonhoi_moive_review_service_app.domain.usecase

import com.example.wonhoi_moive_review_service_app.data.repository.MovieRepository
import com.example.wonhoi_moive_review_service_app.domain.model.Movie

class GetAllMoviesUseCase(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke() : List<Movie> = movieRepository.getAllMovies()

}