package com.example.android.pick

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class PaymentFail : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_fail)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)
        var toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text= "Payment Status"
        var backbutton = findViewById<ImageView>(R.id.backarrow)
        backbutton.setOnClickListener { v ->
            finish()
        }
    }
}