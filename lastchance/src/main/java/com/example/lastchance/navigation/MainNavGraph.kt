package com.example.lastchance.navigation

import android.app.Activity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.example.lastchance.KitConnectionScreen
import com.example.lastchance.WaitingScreen

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.MAIN,
        startDestination = MainScreen.KitConnection.route
    ) {
        addKitConnectionScreen(navController, this)
        addWaitingScreen(navController,this)
    }
}

private fun addKitConnectionScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = MainScreen.KitConnection.route) {
        KitConnectionScreen(
            navigateToWaitingScreen = {
                navController.navigate(MainScreen.Waiting.route)
            }
        )
    }
}

private fun addWaitingScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = MainScreen.Waiting.route) {

        WaitingScreen(

        )
    }
}


sealed class MainScreen(val route: String) {
    object KitConnection : MainScreen(route = "KIT_CON")
    object Waiting : MainScreen(route = "WAITING")
}