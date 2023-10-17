package com.example.challenge2_binar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge2_binar.R
import com.example.challenge2_binar.databinding.ItemKategoriMenuBinding
import com.example.challenge2_binar.produk.KategoriMenu


class MenuAdapterHorizontal(private val data: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemKategoriMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuKategoriHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewKategoriHolder = holder as MenuKategoriHolder
        viewKategoriHolder.bindContent(data[position] as KategoriMenu)
    }

    override fun getItemCount(): Int = data.size


}

class MenuKategoriHolder(private val binding: ItemKategoriMenuBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bindContent(kategoriMenu: KategoriMenu) {
        binding.imgView.setImageResource(kategoriMenu.imgMenu)
        binding.tvMenuKategori.text = kategoriMenu.namaMenu
    }
}
