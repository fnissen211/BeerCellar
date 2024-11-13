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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.beercellar.data.model.Beer
import com.example.beercellar.data.repository.BeerRepository
import com.example.beercellar.models.AuthenticationViewModel
import com.example.beercellar.models.AuthenticationViewModelProvider
import com.example.beercellar.ui.screens.AddBeerScreen
import com.example.beercellar.ui.screens.BeerList
import com.example.beercellar.ui.screens.CreateAccountScreen
import com.example.beercellar.ui.screens.LoginScreen
import com.example.beercellar.ui.screens.BeerDetails
import com.example.beercellar.ui.theme.BeerCellarTheme
import com.google.firebase.auth.FirebaseUser

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

object BeerRepositoryProvider {
    val instance = BeerRepository()
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val authenticationViewModel: AuthenticationViewModel = AuthenticationViewModelProvider.instance
    val beers = BeerRepositoryProvider.instance.beers.value
    val errorMessage = BeerRepository().errorMessage

    NavHost(navController = navController, startDestination = NavRoutes.Login.route) {
        composable(NavRoutes.Login.route) {
            LoginScreen(
                modifier,
                user = authenticationViewModel.user,
                message = authenticationViewModel.message,
                signIn = { email, password -> authenticationViewModel.signIn(email, password) },
                navigateToHome = { navController.navigate(NavRoutes.Home.route) },
                navigateToCreateAccount = {navController.navigate(NavRoutes.CreateAcc.route) }
            )
        }
        composable(NavRoutes.CreateAcc.route) {
            CreateAccountScreen(
                modifier,
                user = authenticationViewModel.user,
                register = { email, password -> authenticationViewModel.register(email, password) },
                navigateToHome = { navController.navigate(NavRoutes.Home.route) },
                navigateToLoginScreen = { navController.navigate(NavRoutes.Login.route) })
        }
        composable(NavRoutes.Home.route) {
            BeerList(
                beers = beers,
                errorMessage = "",
                sortByAbv = { bool -> BeerRepositoryProvider.instance.sortBeersAbv(bool)},
                sortByName = {bool -> BeerRepositoryProvider.instance.sortBeersName(bool)},
                onItemClick = { beer -> navController.navigate(NavRoutes.BeerDetails.route + "/${beer.id}") },
                onItemDelete = { beer -> BeerRepositoryProvider.instance.deleteBeer(beer.id)},
                singOut = {
                    authenticationViewModel.signOut()
                    navController.navigate(NavRoutes.Login.route) {
                        popUpTo(NavRoutes.Home.route) { inclusive = true }
                    }
                },
                onAdd = { navController.navigate(NavRoutes.AddBeer.route)}
            )
        }
        composable(
            NavRoutes.BeerDetails.route + "/{BeerId}",
            arguments = listOf(navArgument("BeerId") { type = NavType.IntType })
        ) { backstackEntry ->
            val beerId = backstackEntry.arguments?.getInt("BeerId")
            val beer = beers.find { it.id == beerId } ?: Beer(0, "", "", "", "", 0.0, 0.0, "", 0)
            BeerDetails(
                beer = beer,
                modifier = modifier,
                onUpdate = {id: Int, updateBeer: Beer -> BeerRepositoryProvider.instance.updateBeers(id, updateBeer)},
                onNavigateBack = {navController.popBackStack()}
            )
        }
        composable(NavRoutes.AddBeer.route) {
            AddBeerScreen(
                addBeer = { beer -> BeerRepositoryProvider.instance.addBeer(beer) },
                navigateBack = { navController.navigate(NavRoutes.Home.route) })
        }
    }
}