package com.example.android.pick

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.android.pick.api.ApiClient
import com.example.android.pick.api.ApiInterface
import com.example.android.pick.model.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Registration : AppCompatActivity() {


    private val TAG = "TAG"


    lateinit var fname :EditText
    lateinit var lname :EditText
    lateinit var email :EditText
    lateinit var number :EditText
    lateinit var pass :EditText

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        val pd1 = ProgressDialog(this)
        pd1.setMessage("Please Wait")


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)
        val registerButton = findViewById<Button>(R.id.register)
        val signinRegisterButton = findViewById<TextView>(R.id.signin_register)

        signinRegisterButton.setOnClickListener { v->
            finish()
         }

        registerButton.setOnClickListener { v->


            fname = findViewById(R.id.fname)
            lname = findViewById(R.id.lname)
            email = findViewById(R.id.email)
            number = findViewById(R.id.number)
            pass = findViewById(R.id.pass)

            val _fname = fname.text.toString()
            val _lname = lname.text.toString()
            val _email= email.text.toString()
            val _number = number.text.toString()
            val _pass = pass.text.toString()

            if (validation()) {
                pd1.show()



                val api: ApiInterface? = ApiClient.client?.create(ApiInterface::class.java)
                val login: Call<DefaultResponse> =
                    api?.createUser(_fname, _lname, _email, _number, _pass)!!

                login.enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {

                        pd1.show()
                        Log.d(TAG, response.body()?.result_code.toString())
                        if (response.body()?.result_code == 1) {
                            //get username
//                            val user: String = response.body()?.status.toString()
                            finish()

                            startActivity(Intent(this@Registration, Signin::class.java))
                            pd1.dismiss()
                            Toast.makeText(
                                this@Registration,
                                "Successfully Registered!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@Registration,
                                "email already registered!",
                                Toast.LENGTH_LONG
                            ).show()
                            pd1.dismiss()

                            Log.d(TAG, response.body()?.status.toString())
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                        Toast.makeText(this@Registration, t.localizedMessage, Toast.LENGTH_SHORT)
//                            .show()
                        Toast.makeText(this@Registration, "Server Down! Please Try Again", Toast.LENGTH_SHORT)
                            .show()
                        Log.d(TAG, t.localizedMessage.toString())
                    }
                })
            }

    }
}
    fun validation(): Boolean {
        var value = true

        val _fname = fname.text.toString().trim()
        val _lname = lname.text.toString().trim()
        val _email = email.text.toString().trim()
        val _number = number.text.toString().trim()
        val _password = pass.text.toString().trim()

        if (_fname.isEmpty()) {
            fname.error = "First Name required"
            fname.requestFocus()
            value = false
        }


        if (_lname.isEmpty()) {
            lname.error = "Last Name required"
            lname.requestFocus()
            value = false
        }

        if (_email.isEmpty()) {
            email.error = "Email required"
            email.requestFocus()
            value = false
        }
        if (_number.isEmpty()) {
            number.error = "Contact Number required"
            number.requestFocus()
            value = false
        }
        if (_password.isEmpty()) {
            pass.error = "New Password required"
            pass.requestFocus()
            value = false
        }

        return value;
    }
}
