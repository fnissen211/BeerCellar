package com.example.beercellar.data.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.beercellar.BeerRepositoryProvider
import com.example.beercellar.data.model.Beer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeerRepository {
    private val url = "https://anbo-restbeer.azurewebsites.net/api/"

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

        Log.d("BeerRepository", "BeerRepository initialized")
        getAllBeers()
    }

    fun getAllBeers() {
        isLoadingBeers.value = true
        beerService.getAllBeers().enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                isLoadingBeers.value = false
                if (response.isSuccessful) {
                    val beerList: List<Beer>? = response.body()
                    beers.value = beerList ?: emptyList()
                    Log.d("BeerService", "Fetched beers: ${beerList?.size}")
                    errorMessage.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                isLoadingBeers.value = false
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
            }
        })
    }

    fun addBeer(beer: Beer) {
        beerService.saveBeer(beer).enqueue(object: Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    val beer = response.body()
                    if (beer != null) {
                        beers.value += beer
                    }
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                errorMessage.value = t.message ?: "No connection to back-end"
            }
        })

    }

    fun deleteBeer(id: Int) {
        beerService.deleteBeer(id).enqueue(object: Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    val beer = response.body()
                    if (beer != null) {
                        beers.value -= beer
                    } else {
                        val message = response.code().toString() + " " + response.message()
                        errorMessage.value = message
                    }
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                errorMessage.value = t.message ?: "No connection to back-end"
            }
        })
    }

    fun updateBeers(beerId: Int, beer: Beer) {
        beerService.updateBeer(beerId, beer).enqueue(object: Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    val updatedBeer = response.body()
                    if (updatedBeer != null) {
                        val index = beers.value.indexOfFirst { it.id == beerId }
                        if (index != -1) {
                            beers.value = beers.value.toMutableList().also {
                                it[index] = updatedBeer
                            }
                        } else {
                            val message = response.code().toString() + " " + response.message()
                            errorMessage.value = message
                        }
                    } else {
                        val message = response.code().toString() + " " + response.message()
                        errorMessage.value = message
                    }
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                errorMessage.value = t.message ?: "No connection to back-end"
            }
        })
    }

    fun sortBeersAbv(sorted: Boolean) {
        val beersList = BeerRepositoryProvider.instance.beers.value ?: emptyList()

        BeerRepositoryProvider.instance.beers.value = if (sorted) {
            beersList.sortedBy { it.abv }
        } else {
            beersList.sortedByDescending { it.abv }
        }
    }

    fun sortBeersName(sorted: Boolean) {
        val beersList = BeerRepositoryProvider.instance.beers.value ?: emptyList()

        BeerRepositoryProvider.instance.beers.value = if (sorted) {
            beersList.sortedBy { it.name }
        } else {
            beersList.sortedByDescending { it.name }
        }
    }
}