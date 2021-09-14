package com.example.harajtask.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.harajtask.R
import com.example.harajtask.databinding.ActivityHarajBinding
import com.example.harajtask.repository.HarajRepository
import com.example.harajtask.ui.viewmodels.HarajModelProviderFactory
import com.example.harajtask.ui.viewmodels.HarajViewModel
import kotlinx.android.synthetic.main.toolbar.*

class HarajActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHarajBinding
    lateinit var viewModel: HarajViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHarajBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toolbar)
        title = getString(R.string.empty)
        setUpDrawerToggle()

        val harajRepository = HarajRepository()
        val viewModelProviderFactory =
            HarajModelProviderFactory(application, harajRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(HarajViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val menuSearch: MenuItem = menu!!.findItem(R.id.action_search)
        val menuShare: MenuItem = menu!!.findItem(R.id.action_share)
        menuSearch.isVisible = true
        menuShare.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpDrawerToggle() {
        val toggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbarLayout.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
        }
        toggle.setToolbarNavigationClickListener {
            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
//                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
//                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        toggle.isDrawerIndicatorEnabled = false
        toggle.setHomeAsUpIndicator(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_baseline_menu_24,
                theme
            )
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }
}