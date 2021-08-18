package com.example.wonhoi_moive_review_service_app.di

import android.app.Activity
import com.example.wonhoi_moive_review_service_app.data.api.*
import com.example.wonhoi_moive_review_service_app.data.preference.PreferenceManager
import com.example.wonhoi_moive_review_service_app.data.preference.SharedPreferenceManager
import com.example.wonhoi_moive_review_service_app.data.repository.*
import com.example.wonhoi_moive_review_service_app.domain.model.Movie
import com.example.wonhoi_moive_review_service_app.domain.usecase.*
import com.example.wonhoi_moive_review_service_app.presentation.home.HomeContract
import com.example.wonhoi_moive_review_service_app.presentation.home.HomeFragment
import com.example.wonhoi_moive_review_service_app.presentation.home.HomePresenter
import com.example.wonhoi_moive_review_service_app.presentation.mypage.MyPageContract
import com.example.wonhoi_moive_review_service_app.presentation.mypage.MyPageFragment
import com.example.wonhoi_moive_review_service_app.presentation.mypage.MyPagePresenter
import com.example.wonhoi_moive_review_service_app.presentation.reviews.MovieReviewsContract
import com.example.wonhoi_moive_review_service_app.presentation.reviews.MovieReviewsFragment
import com.example.wonhoi_moive_review_service_app.presentation.reviews.MovieReviewsPresenter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { Dispatchers.IO }
}

val dataModule = module {
    single { Firebase.firestore }

    single<MovieApi> { MovieFirestoreApi(get()) }
    single<ReviewApi> { ReviewFirestoreApi(get()) }
    single<UserApi> { UserFirestoreApi(get()) }

    single<MovieRepository> { MovieRepositorylmpl(get(), get()) }
    single<ReviewRepository> { ReviewRepositorylmpl(get(), get()) }
    single<UserRepository> { UserRepositorylmpl(get(), get(), get()) }

    // preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }
}

val domainModule = module {
    factory { GetRandomFeaturedMovieUseCase(get(), get()) }
    factory { GetAllMoviesUseCase(get()) }
    factory { GetAllMovieReviewsUseCase(get(), get()) }
    factory { GetMyReviedMoviesUseCase(get(), get(), get()) }
    factory { SubmitReviewUseCase(get(), get()) }
    factory { DeleteReviewUseCase(get()) }
}

val presenterModule = module {
    scope<HomeFragment> {
        scoped<HomeContract.Presenter> { HomePresenter(getSource(), get(), get()) }
    }

    scope<MovieReviewsFragment> {
        scoped<MovieReviewsContract.Presenter> { (movie: Movie) ->
            MovieReviewsPresenter(movie, getSource(), get(), get(), get())
        }
    }

    scope<MyPageFragment> {
        scoped<MyPageContract.Presenter> { MyPagePresenter(getSource(), get()) }
    }

}