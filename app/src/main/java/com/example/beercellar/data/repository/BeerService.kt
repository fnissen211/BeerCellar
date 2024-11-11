package com.example.beercellar.data.repository

import com.example.beercellar.data.model.Beer
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.Call


interface BeerService {
    @GET("beers")
    fun getAllBooks(): Call<List<Beer>>

    @GET("beers/{beersId}")
    fun getBookById(@Path("bookId") bookId: Int): Call<Beer>

    @POST("beers")
    fun saveBook(@Body book: Beer): Call<Beer>

    @DELETE("beers/{id}")
    fun deleteBook(@Path("id") id: Int): Call<Beer>

    @PUT("beers/{id}")
    fun updateBook(@Path("id") id: Int, @Body book: Beer): Call<Beer>
}