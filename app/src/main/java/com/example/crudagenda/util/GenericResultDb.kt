package com.example.crudagenda.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.crudagenda.R

fun <T> Fragment.observeResultGenericDb(
    liveData: LiveData<ResultData<T>>?,
    onError: (() -> Unit)? = null,
    errorDbMessage: String = getString(R.string.error_db),
    onSuccess: (data: T?) -> Unit,
) {
    if (liveData == null) return
    liveData.observe(viewLifecycleOwner) {
        showProgress(it is ResultData.Loading)
        when (it) {
            is ResultData.Error -> {
                if (onError == null) {
                    showErrorApi(
                        messageBody = getString(R.string.error_db)
                    )
                } else {
                    showErrorApi(
                        messageBody = errorDbMessage
                    )
                }
            }

            is ResultData.Success -> {
                onSuccess(it.data)
            }

            else -> {}
        }
    }
}