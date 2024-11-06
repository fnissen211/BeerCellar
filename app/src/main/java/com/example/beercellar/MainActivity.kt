package com.example.beercellar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.beercellar.models.AuthenticationViewModel
import com.example.beercellar.ui.screens.CreateAccountScreen
import com.example.beercellar.ui.screens.HomeScreen
import com.example.beercellar.ui.screens.LoginScreen
import com.example.beercellar.ui.theme.BeerCellarTheme
import com.google.api.Authentication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeerCellarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val authenticationViewModel: AuthenticationViewModel = viewModel()

    NavHost(navController = navController, startDestination = NavRoutes.Login.route) {
        composable(NavRoutes.Login.route) {
            LoginScreen(
                modifier,
                user = authenticationViewModel.user,
                message = authenticationViewModel.message,
                signIn = { email, password -> authenticationViewModel.signIn(email, password) },
                navigateToHome = { navController.navigate(NavRoutes.Home.route) }
            )
        }
        composable(NavRoutes.CreateAcc.route) {
            CreateAccountScreen(
                modifier,
                user = authenticationViewModel.user,
                register = { email, password -> authenticationViewModel.register(email, password) },
                navigateToHome = {
                    navController.popBackStack(NavRoutes.Home.route, inclusive = false)
                })
        }
        composable(NavRoutes.Home.route) {
            HomeScreen()
        }
    }
}