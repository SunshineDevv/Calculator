package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val arrayOfSigns = arrayListOf("+","-","/","*","%","^")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        Log.i("Test","$number")
        binding.buttonClearAll.setOnClickListener { clearAll() }
        binding.buttonClear.setOnClickListener { clearBack() }
        binding.button0.setOnClickListener { setNumberOnView("0") }
        binding.button1.setOnClickListener { setNumberOnView("1") }
        binding.button2.setOnClickListener { setNumberOnView("2") }
        binding.button3.setOnClickListener { setNumberOnView("3") }
        binding.button4.setOnClickListener { setNumberOnView("4") }
        binding.button5.setOnClickListener { setNumberOnView("5") }
        binding.button6.setOnClickListener { setNumberOnView("6") }
        binding.button7.setOnClickListener { setNumberOnView("7") }
        binding.button8.setOnClickListener { setNumberOnView("8") }
        binding.button9.setOnClickListener { setNumberOnView("9") }
        binding.buttonPi.setOnClickListener { setNumberOnView("3.14") }
        binding.buttonDelimiter.setOnClickListener { setTextView(".") }
        binding.buttonAddition.setOnClickListener { setTextView("+") }
        binding.buttonDivision.setOnClickListener { setTextView("/") }
        binding.buttonSubtraction.setOnClickListener { setTextView("-") }
        binding.buttonMultiplication.setOnClickListener { setTextView("*") }
        binding.buttonSolve.setOnClickListener { setTextView("=")
            solveOperation()}
        binding.buttonPercent.setOnClickListener { setTextView("%") }
        binding.buttonExponentation.setOnClickListener { setTextView("^") }
        binding.buttonSquareRoot.setOnClickListener { setNumberOnView("sqrt(") }
        binding.buttonLeft.setOnClickListener { setNumberOnView("(") }
        binding.buttonRight.setOnClickListener { setTextView(")") }
    }

    private fun setNumberOnView(str: String){
        if (binding.editText.text.length == 1 && binding.editText.text.toString() == "0"){
            binding.editText.text = str
        }else{
            binding.editText.append(str)
        }
    }

    private fun setTextView(str: String){

        when{
            binding.editText.text.isNotEmpty() && binding.editText.text.takeLast(1).isDigitsOnly() || binding.editText.text.takeLast(1).toString() == ")"
                    && binding.editText.text.toString() != "0" -> binding.editText.append(str)
            binding.editText.text.length == 1 -> binding.editText.append(str)
            binding.editText.text.takeLast(1).toString().contains(arrayOfSigns) -> {
                var expression = binding.editText.text.toString()
                val operator = expression.takeLast(1)
                val newExpression = operator.replace(operator,str)
                expression = expression.substring(0,expression.length-1)
                binding.editText.text = "$expression$newExpression"
            }
            else -> binding.editText.text = "0"
        }
    }

    private fun clearAll(){
        binding.editText.text = "0"
        binding.solvedOperationText.text = ""
    }

    private fun clearBack(){
        val str = binding.editText.text.toString()
        var res = ""
        if(str.isNotEmpty()){
            res = if(str.length != 1 && str != "0") {
                str.substring(0,str.length-1)
            }else{
                "0"
            }
        }
        binding.editText.text = res
    }

    private fun solveOperation() {
        var expression: String =
            binding.editText.text.toString().substring(0, binding.editText.text.length - 1)
        val exp = ExpressionBuilder(expression).build()
        val result = exp.evaluate()
        binding.solvedOperationText.text = result.toString()
    }

    private fun String.contains(arrayOfSigns: ArrayList<String>): Boolean {
        for (i in arrayOfSigns){
            if (i == "+" || i == "-" || i == "*" || i == "/" || i == "%" || i == "^") return true
        }
        return false
    }

}



