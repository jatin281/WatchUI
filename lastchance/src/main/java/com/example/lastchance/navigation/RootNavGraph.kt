package com.example.lastchance.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient

@Composable
fun RootNavigationGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.MAIN
    ) {
        mainNavGraph(navController = navController)
    }
}


object Graph {
    const val ROOT = "root_graph"
    const val MAIN = "main_graph"
}