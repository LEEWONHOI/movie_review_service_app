package com.example.wonhoi_moive_review_service_app.extension

import java.text.DecimalFormat

fun Float.toDecimalFormatString (format : String) =
    DecimalFormat(format).format(this)