package com.example.crudagenda.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    fun changeTitleToolbar(title: String) {
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
    }

    private fun canShowArrow(showArrow: Boolean) {
        if (showArrow) {
            binding.toolbarLayout.toolbarBack.visible()
        } else {
            binding.toolbarLayout.toolbarBack.gone()
        }
    }


    data class ToolbarConfiguration(
        val showToolbar: Boolean = false,
        val clickOnBack: (() -> Unit)? = null,
        val toolbarTitle: String = "",
        val showArrow: Boolean = true
    )

}