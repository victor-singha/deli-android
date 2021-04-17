package com.example.android.pick

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.android.pick.api.ApiClient
import com.example.android.pick.api.ApiInterface
import com.example.android.pick.model.DefaultResponse
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class Payment : AppCompatActivity() {



    lateinit var sharedPreferences: SharedPreferences

    lateinit var full_name: EditText
    lateinit var email_id: EditText
    lateinit var phone_number: EditText
    lateinit var parcel: EditText

    lateinit var PICKLOCATION: String
    lateinit var DROPLOCATION: String
    lateinit var PICKDATE: String
    lateinit var VEHICLE : String
    lateinit var AMOUNT: String
    lateinit var FULLNAME: String
    lateinit var EMAIL_ID: String
    lateinit var PHONE: String
    lateinit var PARCEL: String


    val PAYPAL_REQ_CODE: Int = 12
    val paypalcongif = PayPalConfiguration()
        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
        .clientId(PaypalClientID.PAYPAL_CLIENT_ID)


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {



        sharedPreferences = getSharedPreferences("SHARED", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()






        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)
        var toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text= "Personal Info & Payment"
        var backbutton = findViewById<ImageView>(R.id.backarrow)
        backbutton.setOnClickListener { v ->
            finish()
        }


        val payButton = findViewById<Button>(R.id.pay)
        val intent = Intent(this, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,paypalcongif)
        startService(intent)

        payButton.setOnClickListener { v ->

            full_name = findViewById(R.id.ed_full_name)
            email_id = findViewById(R.id.ed_email_id)
            phone_number = findViewById(R.id.ed_phone)
            parcel = findViewById(R.id.ed_parcel)

            val _full_name = full_name.text.toString()
            val _email_id = email_id.text.toString()
            val _phone_number = phone_number.text.toString()
            val _parcel = parcel.text.toString()

            if (validation()) {

                editor.putString("FULLNAME",_full_name)
                editor.putString("EMAIL_ID",_email_id)
                editor.putString("PHONE",_phone_number)
                editor.putString("PARCEL",_parcel)
                editor.apply()

                PICKLOCATION = sharedPreferences.getString("PICKLOCATION","").toString()
                DROPLOCATION = sharedPreferences.getString("DROPLOCATION","").toString()
                PICKDATE = sharedPreferences.getString("PICKDATE","").toString()
                VEHICLE = sharedPreferences.getString("VEHICLE","").toString()
                AMOUNT = sharedPreferences.getString("AMOUNT","").toString()
                FULLNAME = sharedPreferences.getString("FULLNAME","").toString()
                EMAIL_ID = sharedPreferences.getString("EMAIL_ID","").toString()
                PHONE = sharedPreferences.getString("PHONE","").toString()
                PARCEL = sharedPreferences.getString("PARCEL","").toString()


                PayPalMethod()

            }
        }
    }
    fun PayPalMethod() {

        val payment = PayPalPayment(BigDecimal(AMOUNT),"USD",VEHICLE, PayPalPayment.PAYMENT_INTENT_SALE)

        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,paypalcongif)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment)

        startActivityForResult(intent,PAYPAL_REQ_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PAYPAL_REQ_CODE){
            if(resultCode == Activity.RESULT_OK){


                Toast.makeText(this@Payment,"Payment Made Successfully", Toast.LENGTH_LONG).show()
                val i = Intent(this@Payment, PaymentSuccess::class.java)
                startActivity(i)
                insertDetails()
                mailOrderDetails()

            }else{
                Toast.makeText(this,"Payment FAIL", Toast.LENGTH_LONG).show()
                val i = Intent(this, PaymentFail::class.java)
                startActivity(i)
            }
        }
    }

    private fun mailOrderDetails() {
        val pd1 = ProgressDialog(this)
        pd1.setMessage("Please Wait")

        pd1.show()
        val api: ApiInterface? = ApiClient.client?.create(ApiInterface::class.java)
        val login: Call<DefaultResponse> =
            api?.mailOrderDetails(PICKLOCATION,DROPLOCATION,PICKDATE,VEHICLE,AMOUNT,FULLNAME,EMAIL_ID,PHONE,PARCEL)!!

        login.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {

                if (response.body()?.result_code == 1) {
                    //get username
//                            val user: String = response.body()?.status.toString()
                    pd1.dismiss()
                } else {
                    Toast.makeText(
                        this@Payment,
                        "failed",
                        Toast.LENGTH_LONG
                    ).show()
                    Toast.makeText(this@Payment,"Order Fail !!", Toast.LENGTH_LONG).show()
                    pd1.dismiss()

                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                        Toast.makeText(this@Registration, t.localizedMessage, Toast.LENGTH_SHORT)
//                            .show()
                Toast.makeText(this@Payment, "Server Down! Please Try Again", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun insertDetails() {
        val pd1 = ProgressDialog(this)
        pd1.setMessage("Please Wait")

        pd1.show()
        val api: ApiInterface? = ApiClient.client?.create(ApiInterface::class.java)
        val login: Call<DefaultResponse> =
            api?.insertOrder(PICKLOCATION,DROPLOCATION,PICKDATE,VEHICLE,AMOUNT,FULLNAME,EMAIL_ID,PHONE,PARCEL)!!

        login.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {

                if (response.body()?.result_code == 1) {
                    //get username
//                            val user: String = response.body()?.status.toString()


                    pd1.dismiss()
                } else {
                    Toast.makeText(
                        this@Payment,
                        "failed",
                        Toast.LENGTH_LONG
                    ).show()
                    Toast.makeText(this@Payment,"Order Fail !!", Toast.LENGTH_LONG).show()
                    pd1.dismiss()

                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                        Toast.makeText(this@Registration, t.localizedMessage, Toast.LENGTH_SHORT)
//                            .show()
                Toast.makeText(this@Payment, "Server Down! Please Try Again", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onDestroy() {
        stopService(Intent(this,PayPalService::class.java))
        super.onDestroy()
    }

    fun validation(): Boolean {
        var value = true

        val _full_name = full_name.text.toString().trim()
        val _email_id = email_id.text.toString().trim()
        val _phone_number = phone_number.text.toString().trim()
        val _parcel = parcel.text.toString().trim()


        if (_full_name.isEmpty()) {
            full_name.error = "Field required"
            full_name.requestFocus()
            value = false
        }

        if (_email_id.isEmpty()) {
            email_id.error = "Field required"
            email_id.requestFocus()
            value = false
        }
        if (_phone_number.isEmpty()) {
            phone_number.error = "Field required"
            phone_number.requestFocus()
            value = false
        }

        if (_parcel.isEmpty()) {
            parcel.error = "Field required"
            parcel.requestFocus()
            value = false
        }



        return value;
    }
}