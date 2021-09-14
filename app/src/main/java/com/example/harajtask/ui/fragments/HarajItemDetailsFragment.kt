package com.example.harajtask.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.harajtask.R
import com.example.harajtask.ui.HarajActivity
import com.example.harajtask.ui.viewmodels.HarajViewModel
import com.example.harajtask.utils.shareTheApp
import kotlinx.android.synthetic.main.fragment_haraj_item_details.*
import android.app.Activity


class HarajItemDetailsFragment : Fragment(R.layout.fragment_haraj_item_details) {

    lateinit var thiscontext: Context
    lateinit var viewModel: HarajViewModel
    val args: HarajItemDetailsFragmentArgs by navArgs()
    var appTitle: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = (activity as HarajActivity).viewModel
//        thiscontext = container.getContext();

        val harajItemData = args.harajItemData
        tvProductName.text = harajItemData.title
        tvProductDate.text = harajItemData.date.toString()
        tvProductUsername.text = harajItemData.username
        tvProductCity.text = harajItemData.city
        tvProductBody.text = harajItemData.body
//        tvProductBody.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Html.fromHtml(harajItemData.body, Html.FROM_HTML_MODE_COMPACT)
//        } else {
//            Html.fromHtml(harajItemData.body)
//        }

        appTitle = harajItemData.title
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        thiscontext = activity
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()

        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar, menu)

        val menuSearch: MenuItem = menu.findItem(R.id.action_search)
        val menuShare: MenuItem = menu.findItem(R.id.action_share)
        menuSearch.isVisible = false
        menuShare.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                if (appTitle != null)
                    shareTheApp(thiscontext, "", appTitle!!)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}