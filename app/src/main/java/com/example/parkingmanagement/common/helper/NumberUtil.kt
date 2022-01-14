package com.example.parkingmanagement.common.helper

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*


fun Int.percentageOf(total: Int): Double =
    (this.toDouble() * total) / 100

fun Int.percentageOf(total: Double): Double =
    (this.toDouble() * total) / 100

fun Double.toAmericanCurrency() : String {
    val country = "US"
    val language = "en"
    return NumberFormat.getCurrencyInstance(Locale(language, country)).format(this)
}

fun Double.toIndianCurrency() : String {
    val country = "IN"
    val language = "en"
    return NumberFormat.getCurrencyInstance(Locale(language, country)).format(this)
}

fun Double.separateIntegerAndDecimal(): Pair<Int, Double> {

    val bigDecimal = BigDecimal(this.toString())
    val integerValue = bigDecimal.intValueExact()
    val decimalValue: Double = bigDecimal.subtract(BigDecimal(integerValue)).toDouble()

    return integerValue to decimalValue
}

