package com.example.apd

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.graphics.Color


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun calculateAPD(view: View){
        view.hideKeyboard()

        var costPerPack = 0.0
        if (!costPerPackTextView.text.toString().isNullOrEmpty()){
            costPerPack = costPerPackTextView.text.toString().toDouble()
        } else {
            Toast.makeText(this, "Requires Cost per Boozpack!", Toast.LENGTH_LONG).show()
            return
        }

        var countPerPack = 0.0
        if (!countPerPackTextView.text.toString().isNullOrEmpty()){
            countPerPack = countPerPackTextView.text.toString().toDouble()
        } else {
            Toast.makeText(this, "Requires Count per Boozski!", Toast.LENGTH_LONG).show()
            return
        }

        var volumePerUnit = 0.0
        if (!volumePerUnitTextView.text.toString().isNullOrEmpty()){
            volumePerUnit = volumePerUnitTextView.text.toString().toDouble()
        } else {
            Toast.makeText(this, "Requires Volume per Boozski!", Toast.LENGTH_LONG).show()
            return
        }

        var alcoholPer100ML = 0.0
        if (!alcoholPerUnitTextView.text.toString().isNullOrEmpty()){
            alcoholPer100ML = alcoholPerUnitTextView.text.toString().toDouble()
        } else {
            Toast.makeText(this, "Requires Booze per Boozski!", Toast.LENGTH_LONG).show()
            return
        }

        var costPerUnit = costPerPack / countPerPack
        var costPer100ML = (costPerUnit / volumePerUnit) * 100
        var apd = (alcoholPer100ML / costPer100ML)

        var txt = String.format("%.2f Alcohols Per Dollar (ml/$)", apd)
        var txtPostfix = ""
        if (apd > 5.0){
            txtPostfix = " ... get lit!"
        } else if (apd < 2.0) {
            txtPostfix = " ... your mom would be disappointed."
        } else {
            txtPostfix = " ... it's aight."
        }
        recentLogTextView.append(txt+txtPostfix+"\n")

        APDTextView.text = txt
        var rcol = minOf(255, (255*(apd/10.0)).toInt())
        APDTextView.setTextColor(Color.rgb(rcol, 0, 0))


    }
}
