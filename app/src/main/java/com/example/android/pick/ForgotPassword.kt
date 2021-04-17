package com.example.android.pick

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.android.pick.api.ApiClient
import com.example.android.pick.api.ApiInterface
import com.example.android.pick.model.DefaultResponse
import com.example.android.pick.model.ForgotPasswordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPassword : AppCompatActivity() {
    private val TAG = "TAG"

    lateinit var ed_forgot_password_email: EditText

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        val pd1 = ProgressDialog(this)
        pd1.setMessage("Please Wait")


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)
        val submitButton = findViewById<Button>(R.id.submit)
        submitButton.setOnClickListener { v->

            if(validation()){
                pd1.show()
                sent()
            }
        }
    }

    private fun sent() {

        ed_forgot_password_email = findViewById(R.id.ed_forgot_password)

        val pd1 = ProgressDialog(this)
        pd1.setMessage("Please Wait !")

        val _email= ed_forgot_password_email.text.toString()

        val api: ApiInterface? = ApiClient.client?.create(ApiInterface::class.java)
        val login: Call<ForgotPasswordResponse> =
            api?.sendPassword(_email)!!

        login.enqueue(object : Callback<ForgotPasswordResponse> {


            override fun onResponse(
                call: Call<ForgotPasswordResponse>,
                response: Response<ForgotPasswordResponse>
            ) {

                pd1.show()
                Log.d(TAG, response.body()?.result_code.toString())
                if (response.body()?.result_code == 1) {
                    //get username
//                            val user: String = response.body()?.status.toString()
                    finish()
                    pd1.dismiss()
                    Toast.makeText(
                        this@ForgotPassword,
                        "Password sent to your mail!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    pd1.dismiss()
                    finish()
                    startActivity(Intent(this@ForgotPassword,ForgotPassword::class.java))
                    Toast.makeText(
                        this@ForgotPassword,
                        "Email Not Registered!",
                        Toast.LENGTH_LONG
                    ).show()


                    Log.d(TAG, response.body()?.status.toString())
                }
            }

            override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                Toast.makeText(this@ForgotPassword, "Server Down! Please Try Again", Toast.LENGTH_SHORT)
                    .show()
                Log.d(TAG, t.localizedMessage.toString())
                pd1.dismiss()
            }
        })
    }

    fun validation(): Boolean {
        var value = true
        ed_forgot_password_email = findViewById(R.id.ed_forgot_password)
        val _email = ed_forgot_password_email.text.toString().trim()


        if (_email.isEmpty()) {
            ed_forgot_password_email.error = "Email required"
            ed_forgot_password_email.requestFocus()
            value = false
        }


        return value;
    }
}