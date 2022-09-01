package com.example.risingcampw4.Model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityViewModel: ViewModel() {
    val message = MutableLiveData<Boolean>()

    fun sendMessage(isGameOver: Boolean) {
        message.value = isGameOver
    }
}