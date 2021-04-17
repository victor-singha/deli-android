package com.example.android.pick

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class Support : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)
        var toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text= "Support"
        var backbutton = findViewById<ImageView>(R.id.backarrow)
        backbutton.setOnClickListener { v ->
            finish()
        }
    }
}