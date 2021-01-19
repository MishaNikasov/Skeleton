package com.lampa.skeleton.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Activity.hideKeyBoard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.share(url: String?) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share")
    sharingIntent.putExtra(Intent.EXTRA_TEXT, url)
    this.startActivity(Intent.createChooser(sharingIntent, "Share via"))
}

fun String?.isEmailValid(): Boolean {
    return if (this.isNullOrEmpty()) {
        true
    } else this.matches(Patterns.EMAIL_ADDRESS.toRegex())
}
