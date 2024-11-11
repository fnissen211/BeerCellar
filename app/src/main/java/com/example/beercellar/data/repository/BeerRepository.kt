package com.example.beercellar.data.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.beercellar.data.model.Beer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeerRepository {
    private val url = "https://anbo-restbeer.azurewebsites.net/api/beers"

    private val beerService: BeerService
    val beers: MutableState<List<Beer>> = mutableStateOf(listOf())
    val isLoadingBeers = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        beerService = build.create(BeerService::class.java)
        getAllBeers()
    }
    fun getAllBeers() {
        isLoadingBeers.value = true
        beerService.getAllBooks().enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                isLoadingBeers.value = false
                if (response.isSuccessful) {
                    val beerList: List<Beer>? = response.body()
                    beers.value = beerList ?: emptyList()
                    errorMessage.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.d("TUBORG", message)
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                isLoadingBeers.value = false
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.d("TUBORG", message)
            }
        })
    }

    fun addBeer() {

    }

    fun deleteBeer() {

    }

    fun sortBeers() {

    }
}