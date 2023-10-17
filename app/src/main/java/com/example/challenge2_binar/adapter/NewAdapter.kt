package com.example.challenge2_binar.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge2_binar.databinding.ItemMenuGridBinding
import com.example.challenge2_binar.databinding.ItemMenuListBinding
import com.example.challenge2_binar.produk.MenuList


class NewAdapter(private val data: List<Any>, val isGrid: Boolean, private var listener: (MenuList) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isGrid) {
            val binding =
                ItemMenuGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridMenuHolder(binding)

        } else {
            val binding =
                ItemMenuListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LinearMenuHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isGrid) {
            val gridHolder = holder as GridMenuHolder
            gridHolder.onBind(data[position] as MenuList)
            val listenerItem = data[position]
            holder.itemView.setOnClickListener{
                listener(listenerItem as MenuList)
            }

        } else {
            val linearholder = holder as LinearMenuHolder
            linearholder.onBind(data[position] as MenuList)
            val listenerItem = data[position]
            holder.itemView.setOnClickListener{
                listener(listenerItem as MenuList)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}

class GridMenuHolder(private val binding: ItemMenuGridBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun onBind(menuList: MenuList) {
        binding.tvMenu.text =menuList.namaMenu
        binding.tvHarga.text=menuList.hargaMenu.toString()
        binding.imgView.setImageResource(menuList.imgMenu)
    }

}

class LinearMenuHolder(private val binding: ItemMenuListBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun onBind(menuList: MenuList) {
        binding.tvMenuu.text =menuList.namaMenu
        binding.tvHarga.text=menuList.hargaMenu.toString()
        binding.imgView.setImageResource(menuList.imgMenu)
    }

}