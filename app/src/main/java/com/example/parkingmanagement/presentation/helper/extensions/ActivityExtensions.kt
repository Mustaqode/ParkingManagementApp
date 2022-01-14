package com.example.parkingmanagement.presentation.helper.extensions


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
    val dialog = dialogBuilder.create()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window?.setGravity(Gravity.BOTTOM)
    dialogBuilder.apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(positiveButtonText ?: "Yes") { _,_ ->
            positive.invoke()
            dialog.cancel()
        }
        setNegativeButton(negativeButtonText ?: "No") { _,_ ->
            negative?.invoke()
            dialog.cancel()
        }
    }
    dialog.show()
    dialog.setCancelable(false)
}
