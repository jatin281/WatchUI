package com.example.lastchance

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState

@Composable
fun KitConnectionScreen(
    navigateToKitListScreen: () -> Unit,
    navigateToWaitingScreen : ()-> Unit
) {

    val viewModel: WearViewModel = viewModel(LocalContext.current as ComponentActivity)

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
                    onClick = navigateToKitListScreen,
                    label = { Text(viewModel.selectedUpper.value, color = Color.White) },
                    colors = ChipDefaults.secondaryChipColors()
                )
            }
            item {
                Chip(
                    onClick = {
                        showVignette = true
                        vignetteState = VignettePosition.Top
                        navigateToKitListScreen()
                    },
                    label = { Text(viewModel.selectedLower.value, color = Color.White) },
                    colors = ChipDefaults.secondaryChipColors()
                )
            }
            item {
                Chip(
                    onClick = {
                        showVignette = true
                        vignetteState = VignettePosition.Bottom
                        navigateToKitListScreen()
                    },
                    label = { Text(viewModel.selectedSL.value, color = Color.White) },
                    colors = ChipDefaults.secondaryChipColors()
                )
            }
            item {
                Chip(
                    onClick = {
                        showVignette = true
                        vignetteState = VignettePosition.TopAndBottom
                        navigateToKitListScreen()
                    },
                    label = { Text(viewModel.selectedSR.value, color = Color.White) },
                    colors = ChipDefaults.secondaryChipColors()
                )
            }

            item {
                Button(onClick = { navigateToWaitingScreen() }) {
                    Text(text = "Next")
                }
            }
        }
    }
}
