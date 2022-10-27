package com.yazhiyue.stylish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yazhiyue.stylish.data.source.local.StylishDatabase
import com.yazhiyue.stylish.databinding.ActivityMainBinding
import com.yazhiyue.stylish.databinding.BadgeBottomBinding
import com.yazhiyue.stylish.factory.ViewModelFactory
import com.yazhiyue.stylish.util.CurrentFragmentType

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        val databaseDao = StylishDatabase.getInstance(application).stylishDatabaseDao

        val viewModelFactory = ViewModelFactory(databaseDao)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[MainViewModel::class.java]


        binding.viewModel = viewModel


        val navController: NavController = this.findNavController(R.id.my_nav_host_fragment)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)

        setupBadge(viewModel)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        navController.addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {

                R.id.HomeFragment -> CurrentFragmentType.HOME
                R.id.CatalogFragment -> CurrentFragmentType.CATALOG
                R.id.CartFragment -> CurrentFragmentType.CART
                R.id.ProfileFragment -> CurrentFragmentType.PROFILE
                R.id.DetailFragment -> CurrentFragmentType.DETAIL
                else -> viewModel.currentFragmentType.value

            }
        }

    }

    private fun setupBadge(viewModel: MainViewModel) {

        val menuView = binding.bottomNavigationView.getChildAt(0) as BottomNavigationMenuView
        val itemView = menuView.getChildAt(2) as BottomNavigationItemView

        val bindingBadge =
            BadgeBottomBinding.inflate(LayoutInflater.from(this), itemView, true)

        bindingBadge.lifecycleOwner = this

        bindingBadge.viewModel = viewModel
    }

}

