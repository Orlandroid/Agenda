package com.example.crudagenda.util

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.File

fun getImageBitmapFromUri(context: Context, imageUri: Uri): Bitmap {
    return MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
}

fun getImageBase64FromFile(directory: File, nameFile: String): String {
    val stream = ByteArrayOutputStream()
    val bitmap = BitmapFactory.decodeFile("$directory/$nameFile")
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
    val imageBytes = stream.toByteArray()
    return Base64.encodeToString(imageBytes, Base64.DEFAULT)
}

fun getImageBase64FromBitMap(bitmap: Bitmap): String {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}


 fun sendToast(context: Context, message: String) =
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()


fun decodeImageBase64ToBitmap(imageBase64: String): Bitmap {
    val baos = ByteArrayOutputStream()
    var imageBytes: ByteArray = baos.toByteArray()
    imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}


fun getPath(context: Context, uri: Uri?): String? {
    var result: String? = null
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    val cursor: Cursor? = context.contentResolver.query(uri!!, proj, null, null, null)
    if (cursor != null) {
        if (cursor.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(proj[0])
            result = cursor.getString(column_index)
        }
        cursor.close()
    }
    if (result == null) {
        result = "Not found"
    }
    return result
}

