package com.example.parkingmanagement.presentation.helper.extensions


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity

@SuppressLint("InflateParams", "NewApi")
fun Activity.showAlertDialog(
    title: String,
    message: String,
    positive: () -> Unit = {},
    negative: (() -> Unit)? = null,
    negativeButtonText: String? = null,
    positiveButtonText: String? = null
) {
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(positiveButtonText ?: "Yes") { _,_ ->
            positive.invoke()
        }
        setNegativeButton(negativeButtonText ?: "No") { _,_ ->
            negative?.invoke()
        }
        setCancelable(false)
    }.create().also {
        it.window?.setGravity(Gravity.CENTER)
        it.show()
    }
}
