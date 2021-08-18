package com.example.wonhoi_moive_review_service_app.data.api

import com.example.wonhoi_moive_review_service_app.domain.model.Movie
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class MovieFirestoreApi(
    private val firestore: FirebaseFirestore
) : MovieApi {

    override suspend fun getAllMovies(): List<Movie> =
        firestore.collection("movies")
            .get()
            .await()
            .map {
                it.toObject<Movie>()    // toObject -> <T> 형태로 바꿔준다. 즉 Movie 형태로 나온다.
            }

    override suspend fun getMovies(movieIds: List<String>): List<Movie> =
        firestore.collection("movies")
            .whereIn(FieldPath.documentId(), movieIds)  // list any 반환, firebase 에서 설정하는 @documentId 값을 가지고 오는 방법
            .get()
            .await()
            .map{
                it.toObject<Movie>()
            }

}