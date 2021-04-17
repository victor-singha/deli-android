package com.example.android.pick

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.android.pick.api.ApiClient
import com.example.android.pick.api.ApiInterface
import com.example.android.pick.model.DefaultResponse
import com.example.android.pick.model.FullDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Signin : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    private val TAG = "TAG"

    lateinit var email : EditText
    lateinit var pass : EditText

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferences = getSharedPreferences("SHARED", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val pd = ProgressDialog(this)
        pd.setMessage("Please Wait")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)

        email = findViewById(R.id.ed_email)
        pass = findViewById(R.id.ed_password)

        val signButton = findViewById<Button>(R.id.signin)
        val newAccountButton = findViewById<TextView>(R.id.newaccount)
        val forgotPasswordButton = findViewById<TextView>(R.id.forgotpassword)

        signButton.setOnClickListener { v->

            val _email= email.text.toString()
            val _pass = pass.text.toString()

            if (validation()) {
                pd.show()

                val api: ApiInterface? = ApiClient.client?.create(ApiInterface::class.java)
                val login: Call<FullDetailsResponse> =
                    api?.login(_email, _pass)!!

                login.enqueue(object : Callback<FullDetailsResponse> {
                    override fun onResponse(
                        call: Call<FullDetailsResponse>,
                        response: Response<FullDetailsResponse>
                    ) {


                        Log.d(TAG, response.body()?.result_code.toString())
                        if (response.body()?.result_code == 1) {
                            //get username
                            val user_email : String = response.body()?.email_id.toString()
                            editor.putBoolean("REMEMBER",true)
                            editor.putString("EMAIL",user_email)
                            editor.apply()
                            editor.commit()
                            Toast.makeText(
                                this@Signin,
                                user_email + " logged in",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                            startActivity(Intent(this@Signin, MainActivity::class.java))
                            pd.dismiss()
                        } else {
                            Toast.makeText(
                                this@Signin,
                                "Wrong email or password !!",
                                Toast.LENGTH_LONG
                            ).show()
                            pd.dismiss()

                            Log.d(TAG, response.body()?.status.toString())
                        }
                    }

                    override fun onFailure(call: Call<FullDetailsResponse>, t: Throwable) {
                        Toast.makeText(this@Signin, t.localizedMessage, Toast.LENGTH_SHORT)
                            .show()
                        Log.d(TAG, t.localizedMessage.toString())
                    }
                })


            }


        }
        newAccountButton.setOnClickListener { v->
            val i = Intent(this,Registration::class.java)
            startActivity(i)

        }
        forgotPasswordButton.setOnClickListener { v->
            val i = Intent(this,ForgotPassword::class.java)
            startActivity(i)

        }
    }

    fun validation(): Boolean {
        var value = true

        val _email = email.text.toString().trim()
        val _password = pass.text.toString().trim()


        if (_email.isEmpty()) {
            email.error = "Email required"
            email.requestFocus()
            value = false
        }

        if (_password.isEmpty()) {
            pass.error = "Password required"
            pass.requestFocus()
            value = false
        }

        return value;
    }
}