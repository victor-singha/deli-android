package com.example.android.pick

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var drawer: DrawerLayout
    private  val TAG = "TAG"
    lateinit var toolbarEmail : TextView
    lateinit var toolbarSignout: ImageView

    lateinit var pick_location: EditText
    lateinit var drop_location: EditText
    lateinit var date_time_in: EditText
    var vehicle: String = ""
    lateinit var amount: String



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferences = getSharedPreferences("SHARED", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val temail = sharedPreferences.getString("EMAIL","")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)

        toolbarEmail = findViewById(R.id.toolbarEmail)
        toolbarEmail.setText(temail)

        val nextButton: Button = findViewById<Button>(R.id.next)
        var nav = findViewById<NavigationView>(R.id.navigation)
        drawer = findViewById(R.id.drawer)

        var hamburger = findViewById<ImageView>(R.id.hamburger)

        val boxtruck = findViewById<LinearLayout>(R.id.boxtruck)
        val cargovan = findViewById<LinearLayout>(R.id.cargovan)
        val minivan = findViewById<LinearLayout>(R.id.minivan)
        val car = findViewById<LinearLayout>(R.id.car)

        var isBoxtruck: Boolean = false
        var isCargovan: Boolean = false
        var isMinivan: Boolean = false
        var isCar: Boolean = false

        boxtruck.setOnClickListener { v->
            if(isBoxtruck == false) {

                isBoxtruck = true
                var isCargovan: Boolean = false
                var isMinivan: Boolean = false
                var iscar: Boolean = false
                boxtruck.setBackgroundResource(R.drawable.selected)
                cargovan.setBackgroundResource(0)
                minivan.setBackgroundResource(0)
                car.setBackgroundResource(0)

                vehicle = "Boxtruck"
                amount = "49"
                editor.putString("VEHICLE",vehicle)
                editor.putString("AMOUNT",amount)
                editor.apply()

            }}
        cargovan.setOnClickListener { v->
            if(isCargovan == false) {

                isBoxtruck = false
                var isCargovan: Boolean = true
                var isMinivan: Boolean = false
                var iscar: Boolean = false
                boxtruck.setBackgroundResource(0)
                cargovan.setBackgroundResource(R.drawable.selected)
                minivan.setBackgroundResource(0)
                car.setBackgroundResource(0)

                vehicle = "Cargovan"
                amount = "39"
                editor.putString("VEHICLE",vehicle)
                editor.putString("AMOUNT",amount)
                editor.apply()

            }}
        minivan.setOnClickListener { v->
            if(isMinivan == false) {

                isBoxtruck = false
                var isCargovan: Boolean = false
                var isMinivan: Boolean = true
                var iscar: Boolean = false
                boxtruck.setBackgroundResource(0)
                cargovan.setBackgroundResource(0)
                minivan.setBackgroundResource(R.drawable.selected)
                car.setBackgroundResource(0)

                vehicle = "Minivan"
                amount = "29"
                editor.putString("VEHICLE",vehicle)
                editor.putString("AMOUNT",amount)
                editor.apply()

            }}
        car.setOnClickListener { v->
            if(isCar == false) {

                isBoxtruck = false
                var isCargovan: Boolean = false
                var isMinivan: Boolean = false
                var iscar: Boolean = true
                boxtruck.setBackgroundResource(0)
                cargovan.setBackgroundResource(0)
                minivan.setBackgroundResource(0)
                car.setBackgroundResource(R.drawable.selected)

                vehicle = "Car"
                amount = "19"
                editor.putString("VEHICLE",vehicle)
                editor.putString("AMOUNT",amount)
                editor.apply()

            }}

        toolbarSignout = findViewById(R.id.toolbarSignout)
        toolbarSignout.setOnClickListener { v->

            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to Sign Out?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        finish()
                        editor.clear()
                        editor.apply()
                        editor.commit()
                        val i = Intent(this,Signin::class.java)
                        startActivity(i)
                        Toast.makeText(this, "Sign Out Successful", Toast.LENGTH_SHORT).show()})
                .setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
            val alert: android.app.AlertDialog? = builder.create()
            if (alert != null) {
                alert.show()
            }

        }




//        hamburger.setOnClickListener { v -> drawer.openDrawer(GravityCompat.START) }
        nextButton.setOnClickListener { v ->

            pick_location = findViewById(R.id.ed_pick_location)
            drop_location = findViewById(R.id.ed_drop_location)

            val _pick_location = pick_location.text.toString()
            val _drop_location = drop_location.text.toString()
            val _date_input = date_time_in.text.toString()
            if (validation()){
                editor.putString("PICKLOCATION",_pick_location)
                editor.putString("DROPLOCATION",_drop_location)
                editor.putString("PICKDATE", _date_input)
                editor.apply()
                startActivity(Intent(this, Payment::class.java))

            }
        }





//
//        nav.setNavigationItemSelectedListener { item: MenuItem ->
//            when(item.itemId)
//            {
//                R.id.pickitup -> {
//                    finish()
//                    drawer.closeDrawer(GravityCompat.START)
//                    val i = Intent(
//                        this@MainActivity,
//                        MainActivity::class.java
//                    )
//
//                    startActivity(i)
//
//
//                }
//                R.id.account -> {
//                    val i = Intent(
//                        this@MainActivity,
//                        Account::class.java
//                    )
//                    startActivity(i)
//                    drawer.closeDrawer(GravityCompat.START)
//                }
//                R.id.support -> {
//                    val i = Intent(
//                        this@MainActivity,
//                        Support::class.java
//                    )
//                    startActivity(i)
//                    drawer.closeDrawer(GravityCompat.START)
//
//                }
//                R.id.signout -> {
//                    drawer.closeDrawer(GravityCompat.START)
//
//                    val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
//                    builder.setMessage("Are you sure you want to Sign Out?")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes",
//                            DialogInterface.OnClickListener { dialog, id ->
//                                finish()
//                                editor.clear()
//                                editor.apply()
//                                editor.commit()
//                                val i = Intent(this,Signin::class.java)
//                                startActivity(i)
//                                Toast.makeText(this, "Sign Out Successful", Toast.LENGTH_SHORT).show()})
//                        .setNegativeButton("No",
//                            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
//                    val alert: android.app.AlertDialog? = builder.create()
//                    if (alert != null) {
//                        alert.show()
//                    }
//                }
//            }
//            true}


        date_time_in = findViewById<EditText>(R.id.date_time_input).also {
            it.setInputType(InputType.TYPE_NULL)
            it.setOnClickListener(View.OnClickListener { showDateTimeDialog(it as EditText?) })
        }
    }
    private fun showDateTimeDialog(date_time_in: EditText?) {
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar[Calendar.YEAR] = year
            calendar[Calendar.MONTH] = month
            calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
//            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
//                calendar[Calendar.HOUR_OF_DAY] = hourOfDay
//                calendar[Calendar.MINUTE] = minute
//                val simpleDateFormat = SimpleDateFormat("yy-MM-dd")
//                date_time_in!!.setText(simpleDateFormat.format(calendar.time))
//            }
//            TimePickerDialog(
//                this@MainActivity,
//                timeSetListener,
//                calendar[Calendar.HOUR_OF_DAY],
//                calendar[Calendar.MINUTE],
//                false
//            ).show()
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            date_time_in!!.setText(simpleDateFormat.format(calendar.time))
        }
        DatePickerDialog(this@MainActivity, dateSetListener, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
    }

    fun validation(): Boolean {
        var value = true

        val _pick_location = pick_location.text.toString().trim()
        val _drop_location = drop_location.text.toString().trim()
        val _date_input = date_time_in.text.toString().trim()


        if (_pick_location.isEmpty()) {
            pick_location.error = "Field required"
            pick_location.requestFocus()
            value = false
        }

        if (_drop_location.isEmpty()) {
            drop_location.error = "Field required"
            drop_location.requestFocus()
            value = false
        }

        if (_date_input.isEmpty()) {
            Toast.makeText(
                this,
                " Select A Date",
                Toast.LENGTH_LONG
            ).show()
            date_time_in.requestFocus()

            value = false
        }
        if(vehicle.isEmpty()){
            Toast.makeText(
                this,
                  " Select A Vehicle",
                Toast.LENGTH_LONG
            ).show()
            value = false
        }

        return value;
    }

}