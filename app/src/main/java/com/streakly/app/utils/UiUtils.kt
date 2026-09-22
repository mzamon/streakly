package com.streakly.app.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.R

object UiUtils {
    fun confirm(context: Context, title: String, message: String, positiveRes: Int, onConfirm: () -> Unit) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveRes) { _, _ -> onConfirm() }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    fun info(context: Context, title: String, message: String) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok, null)
            .show()
    }
}
