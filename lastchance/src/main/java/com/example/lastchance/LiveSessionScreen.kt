package com.example.lastchance

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.R
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.ScalingLazyColumn
import com.example.lastchance.theme.LastChanceTheme
import com.example.lastchance.theme.Orange

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveSessionScreen(

) {

    val viewModel: WearViewModel = viewModel(LocalContext.current as ComponentActivity)

    val sessionState = viewModel.sessionState.collectAsState().value
    val dataRate = viewModel.dataRate.collectAsState().value
    val brookCalibStatus = viewModel.brookCalibStatus.collectAsState().value
    val brookStreamStatus = viewModel.brookStreamStatus.collectAsState().value

    Scaffold(

    ) {
        ScalingLazyColumn(
            contentPadding = PaddingValues(top = 40.dp),
            modifier = Modifier.fillMaxWidth()
        ){
            item {

                Column() {
                    Text(text = "Jatin Chandra", color = Color.Black)
                    Text(text = sessionState.name, color = Orange)
                }

            }

                    item {
                        Row(
                            modifier = Modifier
                                .layoutId("details")
                                .padding(20.dp)
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            //needed in watch

                            Column(
                                modifier = Modifier
                            ) {

//                                Text(text = "Jatin Chandra", color = Color.Black)
//
//                                Text(text = sessionState.name, color = Orange)

//                                athlete?.let { Text(text = (it.firstName + " " + it.lastName)) }
//                                    ?: Text(text = "Loading name...")
                            }

                        }
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .layoutId("motion")
                                .padding(20.dp)
                                .fillMaxWidth()
                                .height(540.dp)
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Button(
                                    onClick = {
//                                        viewModel.calibrateSession()
                                              },
                                    modifier = Modifier,
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(Orange)
                                ) {
                                    Text(
                                        "Calibrate",
                                        fontFamily = FontFamily.SansSerif,
                                        color = Color.White
                                    )
                                }

                                Button(
                                    onClick = {
//                                        viewModel.resetSession()
                                              },
                                    modifier = Modifier,
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(Orange)
                                ) {
                                   Text(
                                        "Reset",
                                        fontFamily = FontFamily.SansSerif,
                                        color = Color.White
                                    )
                                }

                                Button(
                                    onClick = {
//                                        viewModel.endSession()
//                                        popBackStack()
                                    },
                                    modifier = Modifier,
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(Orange)
                                ) {
                                   Text(
                                        "End",
                                        fontFamily = FontFamily.SansSerif,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }


                    item {
                        Column(
                            modifier = Modifier
                                .layoutId("battery")
                                .padding(20.dp)
                                .wrapContentHeight()
                        ) {

                            Text(text = "Core Signal Strength: $dataRate", color = Color.Black)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Connectivity: $brookCalibStatus (C), $brookStreamStatus (S)",
                                    color = Color.Black
                                )
                                IconButton(onClick = {
//                                    viewModel.retrySendCalibAndStream()
                                }) {
                                    Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                                }

                            }

                        }
                    }
                }

            }

}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    LastChanceTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LiveSessionScreen()
        }
    }
}