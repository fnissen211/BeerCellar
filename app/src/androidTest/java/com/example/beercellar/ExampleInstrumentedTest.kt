package com.example.beercellar

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beercellar.data.model.Beer
import com.example.beercellar.ui.screens.AddBeerScreen
import com.example.beercellar.ui.screens.LoginScreen
import com.google.firebase.auth.FirebaseUser

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun AddBeerTest() {
        var beerAdded = false
        val addBeer = { beer: Beer -> beerAdded = true }
        var navigatedBack = false
        val navigateBack = { navigatedBack = true }

        rule.setContent {
            AddBeerScreen(addBeer = addBeer, navigateBack = navigateBack)
        }

        rule.onNodeWithText("Name").assertExists()
        rule.onNodeWithText("Abv").assertExists()
        rule.onNodeWithText("User").assertExists()
        rule.onNodeWithText("Brewery").assertExists()
        rule.onNodeWithText("Style").assertExists()
        rule.onNodeWithText("Volume").assertExists()
        rule.onNodeWithText("Picture URL").assertExists()
        rule.onNodeWithText("How many").assertExists()

        rule.onNodeWithText("Name").performTextInput("Test Beer")
        rule.onNodeWithText("Abv").performTextInput("100.0")
        rule.onNodeWithText("User").performTextInput("wow@home.com")
        rule.onNodeWithText("Brewery").performTextInput("Test Brewery")
        rule.onNodeWithText("Style").performTextInput("Test style")
        rule.onNodeWithText("Volume").performTextInput("100.0")
        rule.onNodeWithText("Picture URL").performTextInput("test")
        rule.onNodeWithText("How many").performTextInput("100")

        rule.onNodeWithText("Add").performClick()

        assertTrue(beerAdded)
        assertTrue(navigatedBack)
    }

    @Test
    fun loginTest() {
        var loggedIn = false

        rule.setContent {
            LoginScreen(navigateToHome = { loggedIn = true })
        }

        rule.onNodeWithText("Email").assertExists()
        rule.onNodeWithText("Password").assertExists()

        rule.onNodeWithText("Email").performTextInput("test@test.com")
        rule.onNodeWithText("Password").performTextInput("testtest")

        rule.onNodeWithText("Sign in").performClick()

        rule.waitUntil(timeoutMillis = 10000) { loggedIn }

        assertTrue(loggedIn)
    }

}