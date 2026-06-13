package com.bernardvb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bernardvb.ui.navigation.BernardVBNavGraph
import com.bernardvb.ui.navigation.Screen
import com.bernardvb.ui.theme.BernardVBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BernardVBTheme {
                BernardVBApp()
            }
        }
    }
}

@Composable
private fun BernardVBApp() {
    val navController = rememberNavController()
    val currentEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentEntry?.destination?.route

    val bottomNavScreens = listOf(
        Screen.Home, Screen.Library, Screen.Learn, Screen.Journal, Screen.Profile
    )

    val showBottomBar = bottomNavScreens.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavScreens.forEach { screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.Home.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(screen.iconRes),
                                    contentDescription = screen.label
                                )
                            },
                            label = { Text(screen.label, style = com.bernardvb.ui.theme.BernardType.LabelSmall) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        BernardVBNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

private val Screen.label: String get() = when (this) {
    Screen.Home -> "Home"
    Screen.Library -> "Library"
    Screen.Learn -> "Learn"
    Screen.Journal -> "Journal"
    Screen.Profile -> "Profile"
    else -> ""
}

private val Screen.iconRes: Int get() = when (this) {
    Screen.Home -> R.drawable.ic_home
    Screen.Library -> R.drawable.ic_library
    Screen.Learn -> R.drawable.ic_learn
    Screen.Journal -> R.drawable.ic_journal
    Screen.Profile -> R.drawable.ic_profile
    else -> R.drawable.ic_home
}
