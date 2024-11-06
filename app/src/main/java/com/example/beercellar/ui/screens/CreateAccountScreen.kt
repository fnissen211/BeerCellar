package com.example.beercellar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseUser


@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier,
    user: FirebaseUser? = null,
    message: String = "",
    register: (email: String, password: String) -> Unit = { _, _ -> },
    navigateToHome: () -> Unit = {}
) {

    if (user != null) {
        navigateToHome()
    }

    var _username by remember { mutableStateOf("") }
    var _password by remember { mutableStateOf("") }

    Column(modifier) {
        Text(text = "Create account")

        TextField(value = "Username", onValueChange = {_username = it})

        TextField(value = "Password", onValueChange = {_password = it})

        Row {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Create account")
            }

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Already registered?")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountScreenPreview() {
    CreateAccountScreen()
}

