package com.example.lastchance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WearViewModel @Inject constructor(

): ViewModel() {

    var selectedUpper = mutableStateOf("Select Upper")
    var selectedLower = mutableStateOf("Select Lower")
    var selectedSL = mutableStateOf("Select SL")
    var selectedSR = mutableStateOf("Select SR")

    private val _sessionState = MutableStateFlow<NexusState>(NexusState.Idle)
    val sessionState: StateFlow<NexusState> = _sessionState

    private val _dataRate = MutableStateFlow<String>(listOf("0%","0%","0%","0%","0%","0%","0%","0%","0%","0%","0%","0%").toString())
    val dataRate: StateFlow<String> = _dataRate

    private val _brookCalibStatus = MutableStateFlow<String>("")
    val brookCalibStatus: StateFlow<String> = _brookCalibStatus

    private val _brookStreamStatus = MutableStateFlow<String>("")
    val brookStreamStatus: StateFlow<String> = _brookStreamStatus
}

enum class NexusState {
    Idle, Scan, Uncalibrated, Calibrated
}