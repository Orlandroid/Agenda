package com.example.crudagenda.ui.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.ui.MainActivity
import com.example.crudagenda.util.showProgress

abstract class BaseFragment<ViewBinding : ViewDataBinding>(@LayoutRes protected val contentLayoutId: Int) :
    Fragment() {


    private var _binding: ViewBinding? = null

    protected val binding: ViewBinding
        get() = _binding!!

    val navController by lazy { findNavController() }
    val baseActivity by lazy { requireActivity() as MainActivity }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        return binding.root
    }

    protected abstract fun setUpUi()

    open fun observerViewModel() {

    }


    open fun configureToolbar() = MainActivity.ToolbarConfiguration()

    open fun configSearchView() = MainActivity.SearchViewConfig()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
        (requireActivity() as MainActivity).apply {
            setToolbarConfiguration(configureToolbar())
            setSearchViewConfig(configSearchView())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showProgress(false)
        _binding = null
    }
}