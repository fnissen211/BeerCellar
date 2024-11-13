package com.example.beercellar.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beercellar.data.model.Beer
import com.example.beercellar.models.AuthenticationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerList(
    beers: List<Beer>,
    onItemClick: (Beer) -> Unit,
    onItemDelete: (Beer) -> Unit,
    onAdd: () -> Unit = {},
    sortByName: (up: Boolean) -> Unit = {},
    sortByAbv: (up: Boolean) -> Unit = {},
    singOut: () -> Unit = {}
){
    Scaffold(
        modifier = Modifier,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8B8D7B),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Beer") },
                actions = {
                    IconButton(onClick = { singOut() }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Log out")
                    }
                }
            )
        },


    ) { innerPadding ->
        BeerListPanel(
            beers = beers,
            modifier = Modifier.padding(innerPadding),
            sortByName = sortByName,
            sortByAbv = sortByAbv,
            onBeerSelected = onItemClick,
            onBeerDeleted = onItemDelete,
            addNewBeer = onAdd
        )
    }
}

@Composable
fun BeerListPanel(
    beers: List<Beer>,
    modifier: Modifier = Modifier,
    sortByName: (up: Boolean) -> Unit,
    sortByAbv: (up: Boolean) -> Unit,
    onBeerSelected: (Beer) -> Unit,
    onBeerDeleted: (Beer) -> Unit,
    addNewBeer: () -> Unit
    ) {
    Column(modifier = modifier) {
        val nameUp = "Name \u2191"
        val nameDown = "Name \u2193"
        val abvUp = "Abv \u2191"
        val abvDown = "Abv \u2193"
        var sortNameAscending by remember { mutableStateOf(true) }
        var sortAbvAscending by remember { mutableStateOf(true) }

        Row {
            OutlinedButton(onClick = {
                sortByName(sortNameAscending)
                sortNameAscending = !sortNameAscending
            }) {
                Text(text = if (sortNameAscending) nameDown else nameUp)
            }
            OutlinedButton(onClick = {
                sortByAbv(sortAbvAscending)
                sortAbvAscending = !sortAbvAscending
            }) {
                Text(text = if (sortAbvAscending) abvDown else abvUp)
            }
            OutlinedButton(onClick = { addNewBeer() }) {
                Text(text = "Add beer")
            }
        }
        val orientation = LocalConfiguration.current.orientation
        val columns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2

        LazyVerticalGrid (columns = GridCells.Fixed(columns)) {
            items(beers) { beer ->
                BeerItem(
                    beer = beer,
                    modifier = Modifier,
                    onItemClick = onBeerSelected,
                    onItemDelete = onBeerDeleted
                )
            }
        }
    }
}

@Composable
fun BeerItem(
    beer: Beer,
    modifier: Modifier = Modifier,
    onItemClick: (Beer) -> Unit,
    onItemDelete: (Beer) -> Unit) {
    Card(modifier = modifier
        .padding(4.dp)
        .fillMaxSize(), onClick = { onItemClick(beer) }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = beer.brewery + ": " + beer.name.toString()
            )
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Remove",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemDelete(beer) }
            )
        }
    }
}


@Preview
@Composable
fun BeerListPreview() {
    BeerList(
        beers = listOf(
            Beer(1, "Alice", "Tuborg Brewery", "Tuborg Gold", "Lager", 4.6, 0.5, "https://example.com/tuborg-gold.jpg", 2),
            Beer(2, "Bob", "Heineken Brewery", "Heineken", "Pale Lager", 5.0, 0.33, "https://example.com/heineken.jpg", 1),
            Beer(3, "Charlie", "Guinness Brewery", "Guinness Draught", "Stout", 4.2, 0.44, "https://example.com/guinness-draught.jpg", 3),
            Beer(4, "Daisy", "Carlsberg Brewery", "Carlsberg", "Pilsner", 5.0, 0.5, "https://example.com/carlsberg.jpg", 1),
            Beer(5, "Eve", "Budweiser Brewery", "Budweiser", "Lager", 5.0, 0.33, "https://example.com/budweiser.jpg", 4),
            Beer(6, "Frank", "Corona Brewery", "Corona Extra", "Pale Lager", 4.6, 0.355, "https://example.com/corona.jpg", 2),
            Beer(7, "Grace", "Stella Artois Brewery", "Stella Artois", "Pilsner", 5.2, 0.5, "https://example.com/stella.jpg", 1),
            Beer(8, "Hank", "Blue Moon Brewery", "Blue Moon", "Witbier", 5.4, 0.33, "https://example.com/bluemoon.jpg", 3),
            Beer(9, "Ivy", "Samuel Adams Brewery", "Boston Lager", "Vienna Lager", 5.0, 0.355, "https://example.com/boston-lager.jpg", 2),
            Beer(10, "Jack", "Sierra Nevada Brewery", "Pale Ale", "Pale Ale", 5.6, 0.355, "https://example.com/pale-ale.jpg", 1)
        ),
        onItemClick = {},
        onItemDelete = {},
        sortByName = {},
        sortByAbv = {},
    )
}