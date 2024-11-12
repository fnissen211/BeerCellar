package com.example.beercellar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.beercellar.models.AuthenticationViewModelProvider
import com.google.firebase.auth.FirebaseUser


@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier,
    user: FirebaseUser? = null,
    message: String = "",
    register: (email: String, password: String) -> Unit = { _, _ -> },
    navigateToHome: () -> Unit = {},
    navigateToLoginScreen: () -> Unit = {}
) {

    if (user != null) {
        navigateToHome()
    }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailIsError by remember { mutableStateOf(false) }
    var passwordIsError by remember { mutableStateOf(false) }

    Column(modifier) {
        Text(text = "Create account")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = { Text("Email") },
            isError = emailIsError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        if (emailIsError) {
            Text("Invalid email", color = MaterialTheme.colorScheme.error)
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            isError = passwordIsError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        if (passwordIsError) {
            Text("Invalid password.", color = MaterialTheme.colorScheme.error)
        }

        Row {
            Button(onClick = { register(username, password) }) {
                Text(text = "Create account")
            }

            Button(onClick = { navigateToLoginScreen() }) {
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

