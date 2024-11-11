package com.example.beercellar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.beercellar.data.repository.BeerRepository
import com.example.beercellar.models.AuthenticationViewModel
import com.example.beercellar.ui.screens.BeerList
import com.example.beercellar.ui.screens.CreateAccountScreen
import com.example.beercellar.ui.screens.LoginScreen
import com.example.beercellar.ui.theme.BeerCellarTheme

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
    val beers = BeerRepository().beers.value
    val errorMessage = BeerRepository().errorMessage

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
            BeerList(
                beers = beers,
                errorMessage = "",
                onItemClick = { beer -> navController.navigate(NavRoutes.BeerDetails.route + "/${beer.id}") },
                onItemDelete = {}
            )
        }
    }
}