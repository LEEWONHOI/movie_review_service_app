package com.example.wonhoi_moive_review_service_app.data.repository

import com.example.wonhoi_moive_review_service_app.data.api.MovieApi
import com.example.wonhoi_moive_review_service_app.domain.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MovieRepositorylmpl(
    private val movieApi: MovieApi,
    private val dispatcher: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getAllMovies(): List<Movie> = withContext(dispatcher) {
        movieApi.getAllMovies()
    }

    override suspend fun getMovies(movieIds: List<String>): List<Movie> = withContext(dispatcher) {
        movieApi.getMovies(movieIds)
    }
}