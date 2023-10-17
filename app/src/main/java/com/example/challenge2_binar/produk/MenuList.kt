package com.example.challenge2_binar.produk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuList(
    var imgMenu: Int,
    var namaMenu: String,
    var hargaMenu: Int,
    var description: String,
    var namaToko: String,
    var location: String,
    val maps: String
):Parcelable