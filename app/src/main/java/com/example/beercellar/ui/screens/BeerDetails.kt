package com.example.beercellar.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.beercellar.data.model.Beer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerDetails(
    beer: Beer,
    modifier: Modifier = Modifier,
    onUpdate: (Int, Beer) -> Unit = { id: Int, data: Beer -> },
    onNavigateBack: () -> Unit)
{
    var name by remember { mutableStateOf(beer.name) }
    var brewery by remember { mutableStateOf(beer.brewery) }
    var abv by remember { mutableStateOf(beer.abv.toString()) }
    var volume by remember { mutableStateOf(beer.volume) }
    var style by remember { mutableStateOf(beer.style) }
    var pictureUrl by remember { mutableStateOf(beer.pictureUrl) }
    var howMany by remember { mutableStateOf(beer.howMany) }
    var user by remember { mutableStateOf(beer.user) }


    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = { Text("Book details") })}) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            OutlinedTextField(onValueChange = { name = it },
                value = name,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Title") })
            OutlinedTextField(onValueChange = { abv = it },
                value = abv,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Price") })
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { onNavigateBack() }) {
                    Text("Back")
                }
                Button(onClick = {
                    // TODO validation
                    val data = Beer(user, brewery, name, style, abv.toDouble(), volume, pictureUrl, howMany)
                    onUpdate(beer.id, data)
                    onNavigateBack()
                }) {
                    Text("Update")
                }
            }
        }

    }
}

@Preview
@Composable
fun BeerDetailsPreview() {
    BeerDetails(
        Beer(1, "Alice", "Tuborg Brewery", "Tuborg Gold", "Lager", 4.6, 0.5, "https://example.com/tuborg-gold.jpg", 2),
        modifier = Modifier.fillMaxSize(),
        onNavigateBack = {}
    )
}