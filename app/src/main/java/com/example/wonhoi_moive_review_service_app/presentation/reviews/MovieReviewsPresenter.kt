package com.example.wonhoi_moive_review_service_app.presentation.reviews

import com.example.wonhoi_moive_review_service_app.domain.model.Movie
import com.example.wonhoi_moive_review_service_app.domain.model.MovieReviews
import com.example.wonhoi_moive_review_service_app.domain.model.Review
import com.example.wonhoi_moive_review_service_app.domain.usecase.DeleteReviewUseCase
import com.example.wonhoi_moive_review_service_app.domain.usecase.GetAllMovieReviewsUseCase
import com.example.wonhoi_moive_review_service_app.domain.usecase.SubmitReviewUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MovieReviewsPresenter(
    override val movie: Movie,
    private val view: MovieReviewsContract.View,
    private val getAllMovieReviewsUseCase: GetAllMovieReviewsUseCase,
    private val submitReviewUseCase: SubmitReviewUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase
) : MovieReviewsContract.Presenter {

    override val scope: CoroutineScope = MainScope()

    private var movieReviews : MovieReviews = MovieReviews(null, emptyList())

    override fun onViewCreated() {
        view.showMovieInformation(movie)
        fetchReviews()
    }

    override fun onDestroyView() {}

    override fun requestAddReview(content: String, score: Float) {
        scope.launch {
            try {
                view.showLoadingIndicator()
                val submitReview = submitReviewUseCase(movie, content, score)
                view.showReviews(movieReviews.copy(myReview = submitReview))
            } catch (exception : Exception) {
                exception.printStackTrace()
                view.showErrorToast("리뷰 등록을 실패했어요 😥")
            } finally {
                view.hideLoadingIndicator()
            }
        }
    }

    override fun requestRemoveReview(review: Review) {
        scope.launch {
            try {
                view.showLoadingIndicator()
                deleteReviewUseCase(review)
                view.showReviews(movieReviews.copy(myReview = null))
            } catch (exception : Exception) {
                exception.printStackTrace()
                view.showErrorToast("리뷰 삭제를 실패했어요 😥")
            } finally {
                view.hideLoadingIndicator()
            }
        }
    }

    private fun fetchReviews() = scope.launch {
        try {
            view.showLoadingIndicator()
            val movieReviews = getAllMovieReviewsUseCase(movie.id!!)
            view.showReviews(movieReviews)
        } catch (exception : Exception) {
            exception.printStackTrace()
            view.showErrorDescription("에러가 발생했어요. 😥")
        } finally {
            view.hideLoadingIndicator()
        }
    }
}