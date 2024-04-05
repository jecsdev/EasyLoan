package com.jecsdev.easyloan.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jecsdev.easyloan.ui.state.DashboardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()
}