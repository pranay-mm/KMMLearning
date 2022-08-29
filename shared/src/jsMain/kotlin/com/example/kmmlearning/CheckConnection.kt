package com.example.kmmlearning

import isOnline
import kotlinx.browser.window
import kotlinx.coroutines.*

@JsName("checkConnection")
val checkConnection = CheckConnection()

actual class CheckConnection {
    //fun getNetworkStatus()
    @JsName("getNetworkStatus")
    actual fun getNetworkStatus(onConnectionChange: (Boolean) -> Unit) {
        //TODO: update code to not use global scope
        GlobalScope.launch {
            isOnline().then {
                onConnectionChange(it)
            }.await()
        }

    }
}