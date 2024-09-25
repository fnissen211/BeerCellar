package com.example.beercellar.data.model

import java.net.URL

data class Beer(
    val id: Int,
    val user: String,
    val brewery: String,
    val name: String,
    val style: String,
    val abv: Float,
    val volume: Float,
    val pictureUrl: URL,
    val howMany: Int
)
