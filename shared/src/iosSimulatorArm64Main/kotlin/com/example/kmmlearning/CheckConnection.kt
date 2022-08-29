package com.example.kmmlearning

import platform.posix.AF_INET6
import platform.posix.gethostbyname2
/*
actual class CheckConnection {

    actual fun getNetworkStatus(onConnectionChange: (Boolean) -> Unit) {
        val hostInfo = gethostbyname2("https://mutualmobile.com/", AF_INET6)
        if (hostInfo != null) {
            onConnectionChange(true)
        } else {
            onConnectionChange(false)
        }
    }
}*/


