package com.example.challenge2_binar.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(application: Application)  {

    private val simpleChartDao: SimpleChartDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = SimpleDatabase.getInstance(application)
        simpleChartDao = db.simpleChartDao
    }

    fun insert(simpleChart: SimpleChart) {
        executorService.execute {
            simpleChartDao.insert(simpleChart)
        }
    }

    fun update(simpleChart: SimpleChart) {
        executorService.execute {
            simpleChartDao.update(simpleChart)
        }
    }

    fun deleteById(chartId: Long) {
        executorService.execute {
            simpleChartDao.deleteById(chartId)
        }
    }

    fun deleteAll(){
        executorService.execute {
            simpleChartDao.deleteAll()
        }
    }

    fun getAllItems(): LiveData<List<SimpleChart>> = simpleChartDao.getAllCartItems()

    fun totalPrice() : LiveData<Int> {
        return simpleChartDao.getAllCartItems().map { cartItems ->
            var total = 0
            for (cartItem in cartItems) {
                total += cartItem.itemPrice * cartItem.itemQuantity
            }
            total
        }
    }
}