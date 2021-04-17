package com.example.android.pick

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class LoadingScreen : AppCompatActivity() {


    lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {


        sharedPreferences = getSharedPreferences("SHARED", Context.MODE_PRIVATE)
        var isRemembered = sharedPreferences.getBoolean("REMEMBER", false)
        var email = sharedPreferences.getString("EMAIL","")


        super.onCreate(savedInstanceState)
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_loading_screen)

        val welcomeThread: Thread = object : Thread() {
            override fun run() {
                try {
                    super.run()
                    sleep(1000) //Delay of 1 second
                } catch (e: Exception) {
                } finally {
                    if(isRemembered) {

                        startActivity(
                            Intent(
                                this@LoadingScreen,
                                MainActivity::class.java
                            )
                        )

                        finish()
                    }else
                    {
                        startActivity(
                            Intent(
                                this@LoadingScreen,
                                Signin::class.java
                            )
                        )
                        finish()
                    }
                }
            }
        }
        welcomeThread.start()

    }
}