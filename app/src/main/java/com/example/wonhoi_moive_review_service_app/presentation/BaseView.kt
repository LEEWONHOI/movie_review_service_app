package com.example.wonhoi_moive_review_service_app.presentation

interface BaseView <PresenterT : BasePresenter>{

    val presenter : PresenterT

}