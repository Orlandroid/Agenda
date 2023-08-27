package com.example.crudagenda.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.crudagenda.R
import com.example.crudagenda.databinding.ActivityMainBinding
import com.example.crudagenda.util.click
import com.example.crudagenda.util.gone
import com.example.crudagenda.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var navController: NavController? = null
    private var searchViewConfig = SearchViewConfig()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavController()
    }

    private fun setUpNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    fun showProgress(show: Boolean) {
        if (show) {
            binding.progressBar.visible()
        } else {
            binding.progressBar.gone()
        }
    }

    private fun changeTitleToolbar(title: String) {
        binding.toolbarLayout.toolbarTitle.text = title
    }

    private fun showToolbar(shouldShow: Boolean) {
        if (shouldShow) {
            binding.toolbarLayout.root.visible()
        } else {
            binding.toolbarLayout.root.gone()
        }
    }

    private fun setOnBackButton(clickOnBack: (() -> Unit)?) = with(binding) {
        val clickOnBackButton = if (clickOnBack == null) {
            {
                navController?.popBackStack()
            }
        } else {
            {
                clickOnBack()
            }
        }
        toolbarLayout.toolbarBack.click {
            clickOnBackButton()
        }
    }

    fun setToolbarConfiguration(configuration: ToolbarConfiguration) {
        setOnBackButton(configuration.clickOnBack)
        changeTitleToolbar(configuration.toolbarTitle)
        showToolbar(configuration.showToolbar)
        canShowArrow(configuration.showArrow)
        showSearchView(configuration.showSearchView)
        setUpSearchView()
    }

    private fun canShowArrow(showArrow: Boolean) {
        if (showArrow) {
            binding.toolbarLayout.toolbarBack.visible()
        } else {
            binding.toolbarLayout.toolbarBack.gone()
        }
    }

    private fun showSearchView(show: Boolean) {
        if (show) {
            binding.searchView.visible()
        } else {
            binding.searchView.gone()
        }
    }

    fun setSearchViewConfig(config: SearchViewConfig) {
        searchViewConfig = config
        showDeleteIcon(config.showDeleteIcon)
    }

    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewConfig.onQueryTextSubmit(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewConfig.onQueryTextChange(newText ?: "")
                return false
            }
        })
        binding.imageView.click {
            searchViewConfig.clickOnDeleteIcon()
        }
    }

    private fun showDeleteIcon(show: Boolean) {
        if (show) {
            binding.imageView.visible()
        } else {
            binding.imageView.gone()
        }
    }


    data class ToolbarConfiguration(
        val showToolbar: Boolean = false,
        val clickOnBack: (() -> Unit)? = null,
        val toolbarTitle: String = "",
        val showArrow: Boolean = true,
        val showSearchView: Boolean = false,
    )

    data class SearchViewConfig(
        val showSearchView: Boolean = false,
        val onQueryTextSubmit: (query: String) -> Unit = {},
        val onQueryTextChange: (newText: String) -> Unit = {},
        val showDeleteIcon: Boolean = false,
        val clickOnDeleteIcon: () -> Unit = {}
    )

}