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
import android.widget.TextView


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        costPerPackTextView.requestFocus()
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

    fun getVal(tv: TextView, err: String): Double {
        var result = -1.0
        if (!tv.text.toString().isNullOrEmpty()){
            result = tv.text.toString().toDouble()
        } else {
            Toast.makeText(this, err, Toast.LENGTH_LONG).show()
            result = -1.0
        }
        return result
    }

    fun calculateAPD(view: View){
        view.hideKeyboard()

        var costPerPack = this.getVal(costPerPackTextView, "Requires Cost per Boozpack!")
        var countPerPack = this.getVal(countPerPackTextView, "Requires Count per Boozpack!")
        var volumePerUnit = this.getVal(volumePerUnitTextView, "Requires Volume per Boozski!")
        var alcoholPer100ML = this.getVal(alcoholPerUnitTextView, "Requires Booze per Boozski!")

        if (costPerPack < 0 || countPerPack < 0 || volumePerUnit < 0 || alcoholPer100ML < 0) return

        var costPerUnit = costPerPack / countPerPack
        var costPer100ML = (costPerUnit / volumePerUnit) * 100
        var apd = (alcoholPer100ML / costPer100ML)

        var txt = String.format("%.2f Alcohol Per Dollar (ml/$)", apd)
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
