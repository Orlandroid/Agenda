package com.example.crudagenda.util

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AlertMessageDialog(private val context: Context, private val listener: ListenerAlertDialog) {

    fun showAlertDialog() {
        val alert = MaterialAlertDialogBuilder(context)
        alert.setTitle("Confirmacion")
            .setMessage("¿ Estas seguro que deseas eliminar el contacto ?")
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