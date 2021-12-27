package com.example.crudagenda.util

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AlertMessageDialog(private val context: Context, private val listener: ListenerAlertDialog) {

    fun showAlertDialog(message: String) {
        val alert = MaterialAlertDialogBuilder(context)
        alert.setTitle("Confirmacion")
            .setMessage(message)
            .setPositiveButton("Eliminar") { dialog, _ ->
                listener.btnEliminar()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                listener.btnCancel()
                dialog.dismiss()
            }
        alert.create()
        alert.show()
    }
}