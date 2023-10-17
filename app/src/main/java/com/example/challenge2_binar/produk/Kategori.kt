package com.example.challenge2_binar.produk

import com.example.challenge2_binar.R

object Kategori {
    val kategori : List<Any>
        get() = mutableListOf(
            KategoriMenu(R.drawable.nasi_tahu, "Nasi"),
            KategoriMenu(R.drawable.spaghetti, "Mie"),
            KategoriMenu(R.drawable.cinamon_roll, "Snack"),
            KategoriMenu(R.drawable.eskopi, "Minuman"),
        )
}