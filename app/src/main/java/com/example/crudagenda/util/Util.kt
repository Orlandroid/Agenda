package com.example.crudagenda.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.File

fun getImageBitmapFromUri(context: Context, imageUri: Uri): Bitmap {
    val imageBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
    return imageBitmap
}

fun getImageBase64FromFile(directory: File, nameFile: String): String {
    val stream = ByteArrayOutputStream()
    val bitmap = BitmapFactory.decodeFile("$directory/$nameFile")
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
    val imageBytes = stream.toByteArray()
    return Base64.encodeToString(imageBytes, Base64.DEFAULT)
}

fun getImageBase64FromBitMap(bitmap: Bitmap):String{
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}


fun decodeImageBase64ToBitmap(imageBase64: String): Bitmap {
    val baos = ByteArrayOutputStream()
    var imageBytes: ByteArray = baos.toByteArray()
    imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

