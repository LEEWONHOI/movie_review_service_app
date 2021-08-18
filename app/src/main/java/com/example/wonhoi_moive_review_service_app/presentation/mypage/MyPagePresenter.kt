package com.example.wonhoi_moive_review_service_app.presentation.mypage

import com.example.wonhoi_moive_review_service_app.domain.usecase.GetMyReviedMoviesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MyPagePresenter(
    private val view : MyPageContract.View,
    private val getMyReviewedMovies : GetMyReviedMoviesUseCase
) : MyPageContract.Presenter {

    override val scope: CoroutineScope = MainScope()

    override fun onViewCreated() {
        fetchReviewedMovies()
    }

    override fun onDestroyView() {}

    private fun fetchReviewedMovies() = scope.launch {
        try {
            view.showLoadingIndicator()

            val reviewedMovies = getMyReviewedMovies()
            if (reviewedMovies.isNullOrEmpty()) {
                view.showNoDataDescription("아직 리뷰한 영화가 없어요.\n홈 탭을 눌러 영화 리뷰를 작성해보세요.")
            } else {
                view.showReviewdMovies(reviewedMovies)
            }
        } catch (exception : Exception) {
            exception.printStackTrace()
            view.showErrorDescription("에러가 발생했어요 😥")
        } finally {
            view.hideLoadingIndicator()
        }
    }
}