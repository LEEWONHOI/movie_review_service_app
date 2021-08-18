package com.example.wonhoi_moive_review_service_app.data.api

import com.example.wonhoi_moive_review_service_app.domain.model.Movie
import com.example.wonhoi_moive_review_service_app.domain.model.Review
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class ReviewFirestoreApi(
    private val firestore: FirebaseFirestore
) : ReviewApi {

    override suspend fun getLatestReview(movieId: String): Review? =
        firestore.collection("reviews")
            .whereEqualTo("movieId", movieId)       // 전달받은 movieId
            .orderBy("createdAt", Query.Direction.DESCENDING)   // 생성된 순서가 최신인걸로
            .limit(1)   // 1개만
            .get()
            .await()
            .map {
                it.toObject<Review>()
            }
            .firstOrNull()

    override suspend fun getAllMovieReviews(movieId: String): List<Review> =
        firestore.collection("reviews")
            .whereEqualTo("movieId", movieId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .await()
            .map {
                it.toObject<Review>()
            }

    override suspend fun getAllUserReviews(userId: String): List<Review> =
        firestore.collection("reviews")
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .await()
            .map {
                it.toObject<Review>()
            }

    override suspend fun addReview(review: Review): Review {
        val newReviewReference = firestore.collection("reviews").document()
        val movieReference = firestore.collection("movies").document(review.movieId!!)

        firestore.runTransaction { transaction ->   // Transaction 동시에 get, set 등의 작업을 진행할 때 사용됨. 부분 적용 안되고 실패 시 전부 롤백시킴
            // 트렌잭션의 읽기 작업은 쓰기 작업 이전에 이루어져야 함.
            val movie = transaction.get(movieReference).toObject<Movie>()!!

            val oldAverageScore = movie.averageScore ?: 0f
            val oldNumberOfScore = movie.numberOfScore ?: 0
            val oldTotalScore = oldAverageScore * oldNumberOfScore

            val newNumberOfScore = oldNumberOfScore + 1
            val newAverageScore = (oldTotalScore + (review.score ?: 0f)) / newNumberOfScore

            transaction.set(
                movieReference,
                movie.copy(
                    numberOfScore = newNumberOfScore,
                    averageScore = newAverageScore
                )
            )

            transaction.set(
                newReviewReference,
                review,
                SetOptions.merge()
            )
        }.await()
        // 업데이트 정보 리턴
        return newReviewReference.get().await().toObject<Review>()!!
    }

    override suspend fun removeReview(review: Review) {
        val reviewReference = firestore.collection("review").document(review.id!!)
        val movieReference = firestore.collection("movies").document(review.movieId!!)

        firestore.runTransaction { transaction ->
            val movie = transaction.get(movieReference).toObject<Movie>()!!

            val oldAverageScore = movie.averageScore ?: 0f
            val oldNumberOfScore = movie.numberOfScore ?: 0
            val oldTotalScore = oldAverageScore * oldNumberOfScore

            val newNumberOfScore = (oldNumberOfScore - 1).coerceAtLeast(0)  // 최소값은 0으로 고정
            val newAverageScore =
                if (newNumberOfScore > 0) {
                    (oldTotalScore - (review.score ?: 0f)) / newNumberOfScore
                } else {
                    0f
                }

            transaction.set(
                movieReference,
                movie.copy(
                    numberOfScore = newNumberOfScore,
                    averageScore = newAverageScore
                )
            )

            transaction.delete(reviewReference)
        }.await()
    }
}