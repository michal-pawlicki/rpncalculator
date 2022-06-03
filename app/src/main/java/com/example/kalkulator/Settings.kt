package com.example.kalkulator

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backgroundColorSpinner = findViewById<Spinner>(R.id.spinner)
        val decimalPointSpinner = findViewById<Spinner>(R.id.spinner2)

        val myAdapter = ArrayAdapter(
            this@Settings,
            android.R.layout.simple_list_item_1, resources.getStringArray(R.array.spinnercolors)
        )
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        backgroundColorSpinner.adapter = myAdapter

        val myAdapter2 = ArrayAdapter(
            this@Settings,
            android.R.layout.simple_list_item_1, resources.getStringArray(R.array.decimalpoints)
        )
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        decimalPointSpinner.adapter = myAdapter2

        val goBack = findViewById<Button>(R.id.button)

        goBack.setOnClickListener(View.OnClickListener {
            val decimalValue = decimalPointSpinner.selectedItem.toString()
            val colorValue = backgroundColorSpinner.selectedItem.toString()
            val i = Intent(this@Settings, MainActivity::class.java)
            i.putExtra("decimal", decimalValue)
            i.putExtra("color", colorValue)
            startActivity(i)
        })

        backgroundColorSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                backgroundColor = backgroundColorSpinner.selectedItem.toString()
                if (backgroundColor == "Black")
                {
                    colorid = Color.parseColor("#000000")
                }
                if (backgroundColor == "LightGray")
                {
                    colorid = Color.parseColor("#686868")
                }
                if (backgroundColor == "Gray")
                {
                    colorid = Color.parseColor("#303030")
                }
                if (backgroundColor == "AlmostBlack")
                {
                    colorid = Color.parseColor("#222222")
                }
                backgroundColorSpinner.setBackgroundColor(colorid)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }
    var colorid = Color.parseColor("#686868")
    var backgroundColor = "Gray"
}