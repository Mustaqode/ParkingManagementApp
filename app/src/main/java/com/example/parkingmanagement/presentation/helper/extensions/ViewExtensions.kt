package com.example.parkingmanagement.presentation.helper.extensions

import android.view.View
import android.widget.TextView


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visibleOrGoneBasedOnCondition(condition: () -> Boolean) {
    if (condition())
        this.visible()
    else this.gone()
}

fun View.visibleOrInvisibleBasedOnCondition(condition: () -> Boolean) {
    if (condition())
        this.visible()
    else this.invisible()
}

fun View.goneIf(condition: () -> Boolean){
    if (condition())
        this.gone()
}

fun TextView.getString() = this.text.toString()

fun TextView.getInt() = this.text.toString().toInt()

