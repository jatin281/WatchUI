package com.example.lastchance.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.lastchance.KitConnectionScreen
import com.example.lastchance.KitListScreen
import com.example.lastchance.LiveSessionScreen
import com.example.lastchance.WaitingScreen

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.MAIN,
        startDestination = MainScreen.KitConnection.route
    ) {
        addKitConnectionScreen(navController, this)
        addKitListScreen(navController,this)
        addWaitingScreen(navController,this)
        addLiveSessionScreen(navController,this)
    }
}

private fun addKitConnectionScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = MainScreen.KitConnection.route) {
        KitConnectionScreen(
            navigateToKitListScreen = {
                navController.navigate(MainScreen.KitList.route)
            },
            navigateToWaitingScreen = {
                navController.navigate(MainScreen.Waiting.route)
            }
        )
    }
}

private fun addKitListScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = MainScreen.KitList.route) {

        KitListScreen (
            popBackStack = { navController.popBackStack() }
        )
    }
}

private fun addWaitingScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = MainScreen.Waiting.route) {

        WaitingScreen (
            navigateToLiveSessionScreen = {
                navController.navigate(MainScreen.LiveSession.route)
            }
        )
    }
}

private fun addLiveSessionScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = MainScreen.LiveSession.route) {

        LiveSessionScreen (

        )
    }
}


sealed class MainScreen(val route: String) {
    object KitConnection : MainScreen(route = "KIT_CON")
    object KitList: MainScreen(route = "KIT_LIST")
    object Waiting: MainScreen(route = "WAITING")
    object LiveSession: MainScreen(route = "LIVE_SESSION")
}