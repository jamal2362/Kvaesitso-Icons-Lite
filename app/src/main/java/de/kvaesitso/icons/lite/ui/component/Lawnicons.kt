package de.kvaesitso.icons.lite.ui.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import de.kvaesitso.icons.lite.ui.destination.Home
import de.kvaesitso.icons.lite.ui.theme.LawniconsTheme
import de.kvaesitso.icons.lite.ui.util.Destinations

@Composable
@ExperimentalFoundationApi
@OptIn(ExperimentalAnimationApi::class)
fun Lawnicons() {
    val navController = rememberAnimatedNavController()

    LawniconsTheme {
        SystemUi()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            AnimatedNavHost(
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
