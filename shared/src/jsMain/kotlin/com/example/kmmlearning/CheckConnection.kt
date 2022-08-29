package com.example.kmmlearning

import kotlinx.browser.window

@JsName("checkConnection")
val checkConnection = CheckConnection()

actual class CheckConnection {
    //fun getNetworkStatus()
    @JsName("getNetworkStatus")
    actual fun getNetworkStatus(onConnectionChange: (Boolean) -> Unit) {
        onConnectionChange(window.navigator.onLine)
    }
}