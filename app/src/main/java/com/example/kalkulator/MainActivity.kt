package com.example.kalkulator

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kalkulator.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


       if (intent.extras?.getString("decimal") != null) {
            decimalValue = intent.extras!!.getString("decimal")?.toInt()!!
            backgroundColor = intent.extras!!.getString("color").toString()
       }

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
        binding.settings.setBackgroundColor(colorid)
        binding.background.setBackgroundColor(colorid)
        display()
    }

    private var colorid = Color.parseColor("#686868")
    private var stack = DoubleArray(0)
    private var stack2 = DoubleArray(0)
    private var decimalValue = 3
    private var backgroundColor= "Gray"

    fun allClearAction(view: View)
    {
        stack2 = stack.copyOf()
        stack = DoubleArray(0)
        working.text = ""
        display()
    }

    fun backSpaceAction(view: View)
    {
        val length = working.length()
        if (length > 0)
            working.text = working.text.subSequence(0, length - 1)
    }

    fun numberAction(view: View)
    {
        if(view is Button)
        {
            working.append(view.text)
        }
    }

    @SuppressLint("SetTextI18n")
    fun changeSignAction(view: View)
    {
        if (stack.isNotEmpty()) {
            stack[0] = -stack[0]
        }
        display()
    }

    fun decimalPointAction(view: View)
    {
        if ("." !in working.text)
            working.append(".")

    }


    private fun check()
    {
        if ("null" in firstlayer.text)
        {
            firstlayer.text = "1:"
        }
        if ("null" in secondlayer.text)
        {
            secondlayer.text = "2:"
        }
        if ("null" in thirdlayer.text)
        {
            thirdlayer.text = "3:"
        }
        if ("null" in fourthlayer.text)
        {
            fourthlayer.text = "4:"
        }
    }


    fun moveStackAction(view: View)
    {
        val length = working.length()
        if (length > 0) {
            val new: Double = working.text.toString().toDouble()

            stack = doubleArrayOf(new) + stack
            display()
            working.text = ""
        }
        if (length == 0)
        {
            if(stack.isNotEmpty())
            {
                stack = listOf(stack[0]).toDoubleArray() + stack
                display()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun display()
    {
        firstlayer.text = "1: " + stack.getOrNull(0)?.round(decimalValue).toString()
        secondlayer.text = "2: " + stack.getOrNull(1)?.round(decimalValue).toString()
        thirdlayer.text = "3: " + stack.getOrNull(2)?.round(decimalValue).toString()
        fourthlayer.text = "4: " + stack.getOrNull(3)?.round(decimalValue).toString()
        check()

    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    fun swapAction(view: View)
    {
        val length = stack.size
        if (length > 1)
        {
            val temp = stack[0]
            stack[0] = stack[1]
            stack[1] = temp
            display()
        }
    }

    fun dropAction(view: View)
    {
        stack2 = stack.copyOf()
        val length = stack.size
        if (length > 0) {
            val temp = stack.toMutableList()
            temp.removeAt(0)
            stack = temp.toDoubleArray()
            display()
        }
    }

    fun operationAction(view: View)
    {
        stack2 = stack.copyOf()
        if(view is Button) {
            if (view.text == "√")
            {
                if (stack.isNotEmpty()) {
                    if(stack[0]>=0) {
                        stack[0] = sqrt(stack[0])
                    }
                    else{
                        alert2(view)
                    }
                }
            }
            if (view.text == "÷") {
                if (stack.size > 1) {
                    val temp = stack[1]
                    if (stack[0] != 0.0) {
                        val tempstack = stack.toMutableList()
                        tempstack.removeAt(1)
                        stack = tempstack.toDoubleArray()
                        stack[0] = temp / stack[0]
                    } else {
                        alert(view)
                    }
                }
            }
            else {

                if (stack.size > 1) {
                    val temp = stack[1]
                    if (view.text == "+") {
                        val tempstack = stack.toMutableList()
                        tempstack.removeAt(1)
                        stack = tempstack.toDoubleArray()
                        stack[0] += temp
                    }
                    if (view.text == "-") {
                        val tempstack = stack.toMutableList()
                        tempstack.removeAt(1)
                        stack = tempstack.toDoubleArray()
                        stack[0] = temp - stack[0]
                    }
                    if (view.text == "x") {
                        val tempstack = stack.toMutableList()
                        tempstack.removeAt(1)
                        stack = tempstack.toDoubleArray()
                        stack[0] *= temp
                    }

                    if (view.text == "y^x") {
                        val tempstack = stack.toMutableList()
                        tempstack.removeAt(1)
                        stack = tempstack.toDoubleArray()
                        stack[0] = temp.pow(stack[0])
                    }
                }
            }
            display()
        }
    }

    fun goToSettings(view: View)
    {
        startActivity(Intent(this@MainActivity, Settings::class.java))
    }

    fun undoAction(view: View)
    {
        stack = stack2.copyOf()
        display()
    }

    fun alert(view: View)
    {
        val builder:AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setMessage("Can't divide by zero")

        builder.setPositiveButton("OK", DialogInterface.OnClickListener{
                dialog, _ -> dialog.dismiss()
        })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
    fun alert2(view: View)
    {
        val builder:AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setMessage("Can't square negative number")

        builder.setPositiveButton("OK", DialogInterface.OnClickListener{
                dialog, _ -> dialog.dismiss()
        })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}