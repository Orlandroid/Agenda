package com.example.crudagenda.ui.listaagenda

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentListaAgendaBinding
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListaAgenda : Fragment(), ListenerAlertDialog {


    private var _binding: FragmentListaAgendaBinding? = null

    private val binding get() = _binding!!
    private val viewModel: ViewModelListaAgenda by viewModels()
    private val TAG = "LISTA_AGENDA"
    private val adapter = ListaAgendaAdapter()

    private fun getListener(): ListenerAlertDialog = this

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaAgendaBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        setUpObserver()
        binding.recyclerViewContactos.adapter = adapter
        CoroutineScope(Dispatchers.Main).launch {
            adapter.setData(viewModel.getContactos() as MutableList<Contacto>)
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listaAgenda_to_addContact)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_lista_agenda, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.submenu_eliminar -> {
                val alert = AlertMessageDialog(requireContext(), getListener())
                alert.showAlertDialog("¿Estas seguro que deseas eliminar todos los contactos?")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setUpObserver() {
        viewModel.contactos.observe(viewLifecycleOwner, {
            when (it) {
                is ResultData.Error -> {
                    binding.root.showSnack(it.message!!)
                    visibilityProgres(false)
                }
                is ResultData.Succes -> {
                    visibilityProgres(false)
                    if (it.data != null) {
                        binding.recyclerViewContactos.adapter = adapter
                        adapter.setData(it.data as MutableList<Contacto>)
                    }
                }
                is ResultData.ErrorNetwork -> {
                    binding.root.showSnack(it.message!!)
                    visibilityProgres(false)
                }
                is ResultData.Loading -> {
                    visibilityProgres(true)
                }
            }
        })
    }

    private fun visibilityProgres(visible: Boolean) {
        if (visible) {
            binding.progressBar.visibility = View.VISIBLE
            return
        }
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun btnCancel() {
        Log.w(TAG, "CANCEL")
    }

    override fun btnEliminar() {
        Log.w(TAG, "Eliminado")
        viewModel.deleteAllContacts()
        CoroutineScope(Dispatchers.Main).launch {
            adapter.setData(viewModel.getContactos() as MutableList<Contacto>)
        }
    }
}