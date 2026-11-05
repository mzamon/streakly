package com.streakly.app.ui.common

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.WindowCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import android.text.Editable
import android.text.TextWatcher

fun Context.dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun View.snack(text: CharSequence) {
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).show()
}

fun View.snack(@StringRes res: Int) {
    Snackbar.make(this, res, Snackbar.LENGTH_LONG).show()
}

/** Shows a confirmation dialog; [onConfirm] runs only when the positive button is pressed. */
fun Context.confirm(
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes positive: Int,
    onConfirm: () -> Unit
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton(com.streakly.app.R.string.cancel, null)
        .setPositiveButton(positive) { _, _ -> onConfirm() }
        .show()
}

/** Clears the inline error as soon as the user edits the field again. */
fun TextInputLayout.clearErrorOnType() {
    editText?.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            error = null
        }
        override fun afterTextChanged(s: Editable?) {}
    })
}

val TextInputLayout.text: String get() = editText?.text?.toString()?.trim() ?: ""

object SystemBars {
    /** Use light icons on dark artwork (splash, welcome) regardless of the chosen theme. */
    fun lightIcons(activity: Activity, darkIconsWanted: Boolean) {
        val controller = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
        controller.isAppearanceLightStatusBars = darkIconsWanted
        controller.isAppearanceLightNavigationBars = darkIconsWanted
    }
}
