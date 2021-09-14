package com.example.harajtask.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.harajtask.HarajTaskApp.Companion.getAppInstance
import com.example.harajtask.R
import com.example.harajtask.adapters.BaseRecyclerAdapter
import com.example.harajtask.databinding.ItemHarajBinding
import com.example.harajtask.models.HarajModel
import com.example.harajtask.ui.HarajActivity
import com.example.harajtask.ui.viewmodels.HarajViewModel
import com.example.harajtask.utils.Resource
import com.example.harajtask.utils.timeAgo
import kotlinx.android.synthetic.main.fragment_haraj_items.*
import kotlinx.android.synthetic.main.item_haraj.view.*

class HarajItemsFragment : Fragment(R.layout.fragment_haraj_items) {

    lateinit var viewModel: HarajViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = (activity as HarajActivity).viewModel

        setupRecyclerView()

        viewModel.harajItems.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { harajModel ->
                        hiCartAdapter.addItems(harajModel)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.status?.let { message ->
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        rvharajItems.apply {
            adapter = hiCartAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        pbHarajItemsProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        pbHarajItemsProgressBar.visibility = View.VISIBLE
    }

    private var hiCartAdapter: BaseRecyclerAdapter<HarajModel, ItemHarajBinding> =
        object : BaseRecyclerAdapter<HarajModel, ItemHarajBinding>() {
            override val layoutResId: Int = R.layout.item_haraj

            override fun onBindData(
                model: HarajModel,
                position: Int,
                dataBinding: ItemHarajBinding
            ) {
                if (position % 2 == 0) dataBinding.clItemCard.setBackgroundColor(Color.parseColor("#F9FBFF"))
                else dataBinding.clItemCard.setBackgroundColor(Color.parseColor("#FEFFFE"))
                Glide.with(getAppInstance())
                    .load(model.thumbURL)
                    .into(dataBinding.ivHarajItemThumb)
//            tvHarajItemName.text = model.title
                dataBinding.tvHarajItemDate.text = timeAgo(model.date)
//            tvHarajItemUsername.text = model.username
//            tvHarajItemCity.text = model.city
                if (model.commentCount > 0) {
                    dataBinding.ivHarajItemComCount.visibility = View.VISIBLE
                    dataBinding.tvHarajItemComCount.visibility = View.VISIBLE
                    dataBinding.tvHarajItemComCount.text = "(${model.commentCount})"
                }
            }

            override fun onItemClick(
                view: View,
                model: HarajModel,
                position: Int,
                dataBinding: ItemHarajBinding
            ) {
                val bundle = Bundle().apply {
                    putSerializable("harajItemData", model)
                }
                findNavController().navigate(
                    R.id.action_harajItemsFragment_to_harajItemDetailsFragment,
                    bundle
                )
            }

            override fun onItemLongClick(view: View, model: HarajModel, position: Int) {}
        }
}