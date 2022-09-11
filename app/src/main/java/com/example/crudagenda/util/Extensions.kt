package com.example.crudagenda.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun View.showSnack(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.showToast(message: String, durationShort: Boolean = false) {
    if (durationShort) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        return
    }
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ImageView.getImageLikeBitmap(): Bitmap {
    return (this.drawable as BitmapDrawable).bitmap
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.openGaleryToChoseImage(resultLauncher: ActivityResultLauncher<Intent>) {
    val gallery = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
    resultLauncher.launch(gallery)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun ImageView.changeDrawableColor(color: Int){
    this.setColorFilter(resources.getColor(color))
}
