package com.example.wonhoi_moive_review_service_app.extension

import android.view.View
import androidx.annotation.Px

@Px
// dp 를 픽셀로 변환
fun View.dip(dipValue : Float) = context.dip(dipValue)

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}