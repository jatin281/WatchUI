package com.example.lastchance

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState

@Composable
fun KitConnectionScreen(
    navigateToWaitingScreen: () -> Unit
) {

    val listState = rememberScalingLazyListState()
    var vignetteState by remember { mutableStateOf(VignettePosition.TopAndBottom) }
    var showVignette by remember { mutableStateOf(true) }

    Scaffold(
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = listState,
                modifier = Modifier
            )
        },
        vignette = {
            if (showVignette) {
                Vignette(vignettePosition = vignetteState)
            }
        },
        timeText = {
            TimeText()
        }
    ) {
        ScalingLazyColumn(
            contentPadding = PaddingValues(top = 40.dp),
            state = listState,
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Chip(
                    onClick = navigateToWaitingScreen,
                    label = { Text("Select Upper") },
                    colors = ChipDefaults.secondaryChipColors()
                )
            }
            item {
                Chip(
                    onClick = {
                        showVignette = true
                        vignetteState = VignettePosition.Top
                        navigateToWaitingScreen()
                    },
                    label = { Text("Select Lower") },
                    colors = ChipDefaults.secondaryChipColors()
                )
            }
            item {
                Chip(
                    onClick = {
                        showVignette = true
                        vignetteState = VignettePosition.Bottom
                        navigateToWaitingScreen()
                    },
                    label = { Text("Select UC") },
                    colors = ChipDefaults.secondaryChipColors()
                )
            }
            item {
                Chip(
                    onClick = {
                        showVignette = true
                        vignetteState = VignettePosition.TopAndBottom
                        navigateToWaitingScreen()
                    },
                    label = { Text("Select LC") },
                    colors = ChipDefaults.secondaryChipColors()
                )
            }
//            items(20) {
//                Chip(
//                    onClick = { },
//                    label = { Text("List item $it") },
//                    colors = ChipDefaults.secondaryChipColors()
//                )
//            }
        }
    }
}
