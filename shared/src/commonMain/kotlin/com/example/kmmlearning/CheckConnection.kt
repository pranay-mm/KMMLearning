package com.example.kmmlearning

expect class CheckConnection {
    //val isAvailableNetwork: MutableStateFlow<Boolean>
    fun getNetworkStatus(onConnectionChange: (Boolean) -> Unit)
    //fun getNetworkStatus()
}