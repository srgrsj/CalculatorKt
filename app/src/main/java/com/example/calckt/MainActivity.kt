package com.example.calckt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.calckt.databinding.ActivityMainBinding
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var expression: String = ""

    private var canAddDecimal = true

    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var total = 0.0

    private var operation = Operations.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_old)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val numbersIds = binding.numbers.referencedIds

        for (id in numbersIds) {

            var buttonText: String

            (binding.root.findViewById<Button>(id)).setOnClickListener {

                if (binding.realScreenN.text.length > 9) {
                    canAddDecimal = false
                }

                if (canAddDecimal) {

                    buttonText = (it as Button).text.toString()
                    expression += buttonText

                    binding.realScreenN.append(buttonText)

                }
            }
        }

        val operationsIds = binding.operations.referencedIds

        for (id in operationsIds) {
            var buttonId: String

            (binding.root.findViewById<Button>(id)).setOnClickListener {

                if (expression.isNotEmpty() && operation == Operations.EMPTY) {

                    firstNumber = expression.toDouble()
                    clearScreen()

                    when (it) {
                        binding.plusBt -> {
                            operation = Operations.PLUS
                            return@setOnClickListener
                        }

                        binding.minusBt -> {
                            operation = Operations.MINUS
                            return@setOnClickListener
                        }

                        binding.divisionBt -> {
                            operation = Operations.DIVISION
                            return@setOnClickListener
                        }

                        binding.multiplyBt -> {
                            operation = Operations.MULTIPLY
                            return@setOnClickListener
                        }


                        binding.percentBt -> {
                            operation = Operations.PERCENT
                            return@setOnClickListener
                        }
                    }
                }
            }
        }

        binding.acBt.setOnClickListener() {
            ac()
        }

        binding.commaBt.setOnClickListener() {

            if (expression.isNotEmpty()) {
                if ("." !in expression) {
                    expression += "."
                }
            } else {
                expression = "0."
            }
            binding.realScreenN.text = expression

        }

        binding.plusMinusBt.setOnClickListener() {
            if (expression.isNotEmpty()) {
                val result = (binding.realScreenN.text.toString().toDouble() * -1)

                binding.realScreenN.text =
                    if (result % 1 == 0.0) result.toInt().toString()
                    else result.toString()

                expression = binding.realScreenN.text.toString()
            } else {
                return@setOnClickListener
            }

        }

        binding.equalBt.setOnClickListener() {

            if (operation != Operations.EMPTY) {

                secondNumber = expression.toDouble()

                expression = when (operation) {
                    Operations.PLUS -> (firstNumber + secondNumber).toString()
                    Operations.MINUS -> (firstNumber - secondNumber).toString()
                    Operations.DIVISION -> (firstNumber / secondNumber).toString()
                    Operations.MULTIPLY -> (firstNumber * secondNumber).toString()
                    Operations.PERCENT -> (firstNumber / 100 * secondNumber).toString()
                    Operations.EMPTY -> return@setOnClickListener
                }

                total = expression.toDouble()

                expression = if (total % 1 == 0.0) {
                    total.toInt().toString()
                } else {
                    total.toString()
                }

                binding.realScreenN.text = if (expression.length <= 10) {
                    expression
                } else {
                    String.format(Locale.US, "%7f", expression.toDouble())
                }

                operation = Operations.EMPTY

            }
        }

    }


    private fun ac() {
        operation = Operations.EMPTY
        canAddDecimal = true
        binding.realScreenN.text = ""
        expression = ""
        total = 0.0
        firstNumber = 0.0
        secondNumber = 0.0
    }

    private fun clearScreen() {
        binding.realScreenN.text = ""
        expression = ""
        canAddDecimal = true
    }
}