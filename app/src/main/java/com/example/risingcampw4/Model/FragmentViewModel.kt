package com.example.risingcampw4.Model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentViewModel: ViewModel() {
    val message = MutableLiveData<Boolean>()

    fun sendMessage(isSuccess: Boolean) {
        message.value = isSuccess
    }
}