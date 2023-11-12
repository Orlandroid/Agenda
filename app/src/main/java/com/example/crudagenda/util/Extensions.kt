package com.example.crudagenda.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.crudagenda.R
import com.example.crudagenda.ui.MainActivity
import com.example.crudagenda.util.MainAlert.Companion.ERROR_MESSAGE
import com.example.crudagenda.util.MainAlert.Companion.INFO_MESSAGE_COLOR
import com.example.crudagenda.util.MainAlert.Companion.SUCCESS_MESSAGE
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException

fun View.showSnack(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.showProgress(show: Boolean) {
    if (requireActivity() is MainActivity) {
        (requireActivity() as MainActivity).showProgress(show)
    }
}

fun Fragment.showErrorApi(
    shouldCloseTheViewOnApiError: Boolean = false,
    messageBody: String = getString(R.string.error_service)
) {
    val dialog = MainAlert(kindOfMessage = ERROR_MESSAGE,
        messageBody = messageBody,
        clickOnAccept = {
            if (shouldCloseTheViewOnApiError) {
                findNavController().popBackStack()
            }
        })
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}

fun Fragment.showErrorNetwork(shouldCloseTheViewOnApiError: Boolean = false) {
    val dialog = MainAlert(kindOfMessage = ERROR_MESSAGE,
        messageBody = getString(R.string.verifica_conexion),
        clickOnAccept = {
            if (shouldCloseTheViewOnApiError) {
                findNavController().popBackStack()
            }
        })
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}

fun Fragment.showSuccessMessage(
    message: String = getString(R.string.register_success),
    clickOnOk: () -> Unit = {}
) {
    val dialog = MainAlert(
        kindOfMessage = SUCCESS_MESSAGE,
        messageBody = message,
        clickOnAccept = { clickOnOk() }
    )
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}

fun Fragment.showInfoMessage(
    message: String,
    isTwoButtonDialog: Boolean = false,
    clickOnOk: () -> Unit = {}
) {
    val dialog = MainAlert(
        kindOfMessage = INFO_MESSAGE_COLOR,
        messageBody = message,
        clickOnAccept = { clickOnOk() },
        isTwoButtonDialog = isTwoButtonDialog
    )
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
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

fun ImageView.loadImageWithAnimation(bitmapImage: Bitmap) {
    Glide.with(context).load(bitmapImage).transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(R.drawable.loading_animation).into(this)
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

fun View.click(click: () -> Unit) {
    setOnClickListener { click() }
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

fun TextInputLayout.getText(): String {
    return editText?.text.toString().trim()
}

fun ImageView.changeDrawableColor(color: Int) {
    this.setColorFilter(resources.getColor(color))
}

fun Bitmap.toBase64(): String {
    var result = ""
    val baos = ByteArrayOutputStream()
    try {
        compress(Bitmap.CompressFormat.JPEG, 100, baos)
        baos.flush()
        baos.close()
        val bitmapBytes = baos.toByteArray()
        result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            baos.flush()
            baos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return result
}

fun String.base64StringToBitmap(): Bitmap {
    val baos = ByteArrayOutputStream()
    var imageBytes: ByteArray = baos.toByteArray()
    imageBytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

fun View.createAPopMenu(lisOfMenus: List<String>, clickOnPosition: (position: Int) -> Unit) {
    val popUpMenu = PopupMenu(this.context, this, Gravity.START)
    var idItem = 0
    lisOfMenus.forEach {
        popUpMenu.menu.add(Menu.NONE, idItem, idItem, it)
        idItem++
    }
    popUpMenu.setOnMenuItemClickListener { menuItem ->
        clickOnPosition(menuItem.itemId)
        false
    }
    popUpMenu.show()
}

fun Fragment.showLog(message: String, tag: String = javaClass.name) {
    Log.w(tag, message)
}

fun <T> LiveData<T>.observeOnce(observer: (T) -> Unit) {
    observeForever(object : Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}

fun <T> Fragment.collectLifeCycleFlow(flow: Flow<T>, collector: FlowCollector<T>) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collector)
        }
    }
}

fun <T> NavController.savedStateHandle(key: String, value: T) {
    previousBackStackEntry?.savedStateHandle?.set(key, value)
}

fun <T> NavController.getLiveData(
    viewLifecycleOwner: LifecycleOwner,
    key: String,
    savedStateHandle: SavedStateHandle?.() -> Unit = {},
    listener: (T) -> Unit,
){
    currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)?.observe(viewLifecycleOwner) {
        it?.let {
            listener(it)
            savedStateHandle(previousBackStackEntry?.savedStateHandle)
            if (it == Lifecycle.Event.ON_DESTROY) {
                previousBackStackEntry?.savedStateHandle?.remove<T>(key)
            }
        }
    }
}
