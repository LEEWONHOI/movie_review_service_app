package com.example.wonhoi_moive_review_service_app.extension

import android.content.Context
import androidx.annotation.Px

@Px
// dp를 픽셀로 변환
fun Context.dip(dipValue : Float) = (dipValue * resources.displayMetrics.density).toInt()