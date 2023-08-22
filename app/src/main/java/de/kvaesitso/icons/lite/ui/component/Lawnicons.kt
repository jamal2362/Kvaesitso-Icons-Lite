package de.kvaesitso.icons.lite.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.kvaesitso.icons.lite.ui.destination.Home
import de.kvaesitso.icons.lite.ui.theme.LawniconsTheme
import de.kvaesitso.icons.lite.ui.util.Destinations

@Composable
@ExperimentalFoundationApi
fun Lawnicons() {
    val navController = rememberNavController()

    LawniconsTheme {
        SystemUi()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            NavHost(
                navController = navController,
                startDestination = Destinations.HOME,
            ) {
                composable(route = Destinations.HOME) {
                    Home()
                }
            }
        }
    }
}
