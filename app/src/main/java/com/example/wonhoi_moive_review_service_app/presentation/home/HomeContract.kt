package com.example.wonhoi_moive_review_service_app.presentation.home

import com.example.wonhoi_moive_review_service_app.domain.model.FeaturedMovie
import com.example.wonhoi_moive_review_service_app.domain.model.Movie
import com.example.wonhoi_moive_review_service_app.presentation.BasePresenter
import com.example.wonhoi_moive_review_service_app.presentation.BaseView

interface HomeContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorDescription(message : String)

        fun showMovies(
            featuredMovie: FeaturedMovie?,
            movies : List<Movie>
        )
    }

    interface Presenter : BasePresenter

}