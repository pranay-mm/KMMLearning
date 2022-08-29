package com.example.kmmlearning.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.kmmlearning.CheckConnection
import com.example.kmmlearning.Greeting
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = "loading..."
        val context = this
        val checkConnection = CheckConnection(context)
        findViewById<AppCompatButton>(R.id.checkConnection).setOnClickListener {
            checkConnection.getNetworkStatus{
                val status = if(it) "Connected" else "DisConnected"
                Toast.makeText(context,"Connection Status->$status",Toast.LENGTH_LONG).show()
            }
        }

        mainScope.launch {
            kotlin.runCatching {
                Greeting().greeting()
            }.onSuccess {
                tv.text = it

            }.onFailure {
              tv.text = it.localizedMessage
            }
        }



    }
}
