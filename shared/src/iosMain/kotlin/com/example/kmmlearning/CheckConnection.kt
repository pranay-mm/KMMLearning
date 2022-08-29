package com.example.kmmlearning

import cocoapods.Reachability.Reachability
import platform.Foundation.NSLog
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.posix.AF_INET6
import platform.posix.gethostbyname2

actual class CheckConnection {
    private var reachability: Reachability? = null

    actual fun getNetworkStatus(onConnectionChange: (Boolean) -> Unit) {

        dispatch_async(dispatch_get_main_queue()){
            reachability = Reachability.reachabilityForInternetConnection()

            reachability?.apply {
                reachableBlock = {
                    dispatch_async(dispatch_get_main_queue()) {
                        NSLog("Connected")
                        onConnectionChange(true)
                    }
                }
                unreachableBlock = {
                    dispatch_async(dispatch_get_main_queue()) {
                        NSLog("Disconnected")
                        onConnectionChange(false)
                    }
                }
                startNotifier()
                dispatch_async(dispatch_get_main_queue()) {
                    onConnectionChange(isReachable())
                }
            }
        }
    }
}


