package com.mg.tipcalculator

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.get
import com.mg.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            calculateTip()
        }

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateTip() {
        val StringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = StringInTextField.toDoubleOrNull()

        val TipPrecentage= when (binding.tipOptions.checkedRadioButtonId){
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }


        // Calculate the tip
        var tip = TipPrecentage * cost!!

        // If the switch for rounding up the tip toggled on (isChecked is true), then round up the
        // tip. Otherwise do not change the tip value.
        val roundUp = binding.switchMaterial.isChecked
        if (roundUp) {
            // Take the ceiling of the current tip, which rounds up to the next integer, and store
            // the new value in the tip variable.
            tip = kotlin.math.ceil(tip)
        }

        displayTip(tip)

    }

    @SuppressLint("SetTextI18n")
    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.TipResult.text = "Tip Amount : $formattedTip"
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}