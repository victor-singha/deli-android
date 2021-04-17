package com.example.android.pick

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

class PaymentSuccess : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)
        var toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text= "Payment Status"
        var backbutton = findViewById<ImageView>(R.id.backarrow)


        val parentLayout = findViewById<View>(android.R.id.content)
        Snackbar.make(
            parentLayout,
            "Your order has been placed. Please check your mail for Order details.",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("CLOSE") { }
            .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
            .show()



        backbutton.setOnClickListener { v ->
            finish()
        }


    }
}