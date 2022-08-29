package com.example.kmmlearning

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

actual class CheckConnection constructor(private val context: Context) {
    val isNetworkConnected = MutableStateFlow(false)
    private var connectivityManager: ConnectivityManager? = null

    actual fun getNetworkStatus(onConnectionChange: (Boolean) -> Unit) {
        try {
            if (connectivityManager == null) {
                connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // API 24 and above
                connectivityManager?.registerDefaultNetworkCallback(networkCallback)

                val currentNetwork = connectivityManager?.activeNetwork

                val isConnected = currentNetwork == null
                isNetworkConnected.value = isConnected
                    Log.d("Connectivity status", if(isConnected) "Connected" else "Disconnected")
            } else {
                // API 23 and below
                val networkRequest = NetworkRequest.Builder().apply {
                    addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    }
                }.build()

                connectivityManager?.registerNetworkCallback(networkRequest, networkCallback)

                @Suppress("DEPRECATION")
                val currentNetwork = connectivityManager?.activeNetworkInfo

                val isConnected = currentNetwork == null || (
                        currentNetwork.type != ConnectivityManager.TYPE_ETHERNET &&
                                currentNetwork.type != ConnectivityManager.TYPE_WIFI &&
                                currentNetwork.type != ConnectivityManager.TYPE_MOBILE
                        )
                isNetworkConnected.value = isConnected
                Log.d("Connectivity status", if(isConnected) "Connected" else "Disconnected")
            }

            Log.d("Connectivity status", "Started")
        } catch (e: Exception) {
            Log.d("Connectivity status", "Failed to start: ${e.message.toString()}")
            e.printStackTrace()
            isNetworkConnected.value = false
        }

        CoroutineScope(Dispatchers.Default).launch {
            isNetworkConnected.collect { status ->
                withContext(Dispatchers.Main) {
                    onConnectionChange(status)
                }
            }
        }
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.d("Connectivity status", "Network available")
            isNetworkConnected.value = true
        }

        override fun onLost(network: Network) {
            Log.d("Connectivity status", "Network lost")
            isNetworkConnected.value = false
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)

            val isConnected =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED) &&
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                        } else { true }

            Log.d("Connectivity status", "Network status: ${if(isConnected){ "Connected" } else { "Disconnected" }}")

            isNetworkConnected.value = isConnected
        }
    }

    /*actual fun getNetworkStatus(onConnectionChange: (Boolean) -> Unit) {
    //actual fun getNetworkStatus() {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var statusOfNetwork=false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        statusOfNetwork = true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        statusOfNetwork = true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        statusOfNetwork = true

                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                statusOfNetwork = true
            }
        }
        onConnectionChange(statusOfNetwork)
    }
*/
    /*private fun setStatus(isConnected: Boolean){
        isAvailableNetwork.value=isConnected
    }*/

}