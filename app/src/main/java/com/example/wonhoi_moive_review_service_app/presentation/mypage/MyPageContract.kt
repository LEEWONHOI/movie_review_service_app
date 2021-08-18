package com.example.wonhoi_moive_review_service_app.presentation.mypage

import com.example.wonhoi_moive_review_service_app.domain.model.ReviewedMovie
import com.example.wonhoi_moive_review_service_app.presentation.BasePresenter
import com.example.wonhoi_moive_review_service_app.presentation.BaseView

interface MyPageContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showNoDataDescription(message : String)

        fun showErrorDescription(message : String)

        fun showReviewdMovies(reviewedMovies : List<ReviewedMovie>)

    }

    interface Presenter : BasePresenter

}