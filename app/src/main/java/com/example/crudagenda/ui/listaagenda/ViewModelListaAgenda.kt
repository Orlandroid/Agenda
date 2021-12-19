package com.example.crudagenda.ui.listaagenda



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.repositorio.ContactoRepository
import com.example.crudagenda.util.NetworkHelper
import com.example.crudagenda.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelListaAgenda @Inject constructor(
    private val contactoRepository: ContactoRepository,
private val networkHelper: NetworkHelper) :
    ViewModel() {


    init {
        getAllContacts()
    }

    private val _contactos = MutableLiveData<ResultData<List<Contacto>>>()
    val contactos: LiveData<ResultData<List<Contacto>>>
        get() = _contactos

    fun deleteAllContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            contactoRepository.deleteAllContactos()
        }
    }

    private fun getAllContacts() {
        if (!networkHelper.isNetworkConnected()){
            _contactos.postValue(ResultData.ErrorNetwork("Error de conexion"))
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _contactos.postValue(ResultData.Loading())
            val responseContactos = contactoRepository.getAllContacs()
            if (responseContactos.isNotEmpty()){
                _contactos.postValue(ResultData.Succes(responseContactos))
                return@launch
            }
            _contactos.postValue(ResultData.Error("Aun no hay ningun contacto en la base de datos"))
        }
    }


}