package com.example.wonhoi_moive_review_service_app.presentation.reviews

import com.example.wonhoi_moive_review_service_app.domain.model.Movie
import com.example.wonhoi_moive_review_service_app.domain.model.MovieReviews
import com.example.wonhoi_moive_review_service_app.domain.model.Review
import com.example.wonhoi_moive_review_service_app.presentation.BasePresenter
import com.example.wonhoi_moive_review_service_app.presentation.BaseView
import com.example.wonhoi_moive_review_service_app.presentation.home.HomeContract

interface MovieReviewsContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorDescription(message: String)

        fun showMovieInformation(movie: Movie)

        fun showReviews(reviews: MovieReviews)

        fun showErrorToast(message: String)
    }

    interface Presenter : BasePresenter {

        val movie: Movie

        fun requestAddReview(content: String, score: Float)

        fun requestRemoveReview(review: Review)
    }
}