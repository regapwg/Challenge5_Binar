package com.example.challenge2_binar.api

import com.example.challenge2_binar.modelCategory.KategoriMenu
import com.example.challenge2_binar.produk.ListMenu
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("category")
    fun getCategory(): Call<KategoriMenu>

    @GET("listmenu")
    suspend fun getList(): ListMenu
}