package com.example.harajtask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.harajtask.BR
import java.util.*

abstract class BaseRecyclerAdapter<T, VB> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var braModelList = ArrayList<T>()

    abstract val layoutResId: Int

    var size: Int? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(
            braModelList[position],
            position,
            (holder as BaseRecyclerAdapter<*, *>.DataViewHolder).mDataBinding as VB
        )
        (holder.mDataBinding as ViewDataBinding).setVariable(
            BR.harajProperties,
            braModelList[position]
        )
        (holder.mDataBinding as ViewDataBinding).setVariable(BR.clickListener, holder.listener)
    }

    override fun getItemCount(): Int =
        if (size == null || braModelList.size <= size!!) braModelList.size else size!!

    abstract fun onBindData(model: T, position: Int, dataBinding: VB)

    abstract fun onItemClick(view: View, model: T, position: Int, dataBinding: VB)

    abstract fun onItemLongClick(view: View, model: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DataViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutResId,
                parent,
                false
            )
        )

    fun setModelSize(aSize: Int) {
        size = aSize
    }

    fun addItems(aList: ArrayList<T>) {
        braModelList.clear()
        braModelList.addAll(aList)
        notifyDataSetChanged()
    }

    fun getModel(): ArrayList<T> {
        return braModelList
    }

    fun sortByHighPrice() {
        notifyDataSetChanged()
    }

    fun addMoreItems(aList: ArrayList<T>) {
        aList.forEach {
            braModelList.add(it)
            notifyItemInserted(braModelList.size - 1)
        }
    }

    fun addNewItem(item: T) {
        braModelList.add(item)
        notifyItemInserted(braModelList.size - 1)
    }

    fun removeItem(pos: Int) {
        braModelList.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun getItem(position: Int): T = braModelList[position]

    fun clearData() {
        braModelList.clear()
        notifyDataSetChanged()
    }

    internal inner class DataViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root), OnClickListener<T> {
        override fun onClick(view: View, t: T) {
            onItemClick(view, t, adapterPosition, mDataBinding)
        }

        var mDataBinding: VB = binding as VB

        val listener: OnClickListener<T>? = this
    }

    interface OnClickListener<T> {
        fun onClick(view: View, t: T)
    }
}