package com.hardrelice.base.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Method


open class MultiTypeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val arrayList = mutableListOf<Any>()
    private val holderMap = hashMapOf<Int, Class<out RecyclerView.ViewHolder?>>()

    fun setItems(items: ArrayList<Any>) {
        arrayList.clear()
        arrayList.addAll(items)
        println(arrayList[10])
        notifyDataSetChanged()
    }

    fun addItem(position: Int, item: Any) {
        arrayList.add(position, item)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun registerType(
        javaClass: Class<*>,
        viewType: Class<out RecyclerView.ViewHolder?>
    ): Boolean {
        if (javaClass.hashCode() in holderMap.keys) return false
        holderMap[javaClass.hashCode()] = viewType
        return true
    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        abstract fun setData(position: Int, data: Any)
        fun getItemView() = itemView
    }

    override fun getItemViewType(position: Int): Int {
        return arrayList[position].javaClass.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return holderMap[viewType]!!
            .getConstructor(ViewGroup::class.java)
            .newInstance(parent)!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val setData: Method = holder.javaClass
            .getDeclaredMethod(
                "setData",
                Int::class.java,
                Any::class.java
            )
        setData(holder, position, arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}