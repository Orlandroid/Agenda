package com.example.crudagenda.util

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
@OptIn(DelicateCoroutinesApi::class)
suspend inline fun <T> safeDbOperation(
    result: MutableLiveData<ResultData<T>>,
    crossinline dbOperation: suspend () -> Unit,
) {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            withContext(Dispatchers.Main) {
                result.value = ResultData.Loading()
            }
            dbOperation()
            Log.w("Android", "dbOperation")
        } catch (e: Exception) {
            Log.w("Error", e.message.toString())
            withContext(Dispatchers.Main) {
                result.value = ResultData.Error(e.message)
            }
        }
    }
}*/