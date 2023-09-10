package com.sw.cdtimer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TimerViewModel: ViewModel() {
    val minutes: MutableStateFlow<String> = MutableStateFlow("")
    val seconds: MutableStateFlow<String> = MutableStateFlow("")
    val isRunning: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val currentTime: MutableStateFlow<String> = MutableStateFlow("0")

    fun start() {
        try {
            val minutesInt = minutes.value.toInt()
            val secondsInt = seconds.value.toInt()
            isRunning.value = true
            var secondCount = minutesInt*60 + secondsInt
            viewModelScope.launch(Dispatchers.Default) {
                while (isRunning.value && secondCount >= 0) {
                    val mm = secondCount/60
                    val ss = secondCount%60
                    currentTime.value = "$mm:$ss"
                    delay(1000)
                    secondCount = secondCount-1
                }
                isRunning.value = false
            }
        } catch (e:Exception) {

        }
    }


    fun stop() {
        isRunning.value = false
    }

}