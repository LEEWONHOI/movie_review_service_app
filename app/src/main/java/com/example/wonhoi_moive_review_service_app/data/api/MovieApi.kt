package com.example.wonhoi_moive_review_service_app.data.api

import com.example.wonhoi_moive_review_service_app.domain.model.Movie

interface MovieApi {

    suspend fun getAllMovies() : List<Movie>

    suspend fun getMovies(movieIds : List<String> ) : List<Movie>

}