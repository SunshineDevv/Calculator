package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
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
            solveOperation() }
        binding.buttonPercent.setOnClickListener { setTextView("%") }
        binding.buttonExponentation.setOnClickListener { setTextView("^") }
        binding.buttonSquareRoot.setOnClickListener { setSquareOnView("sqrt(") }
        binding.buttonLeft.setOnClickListener { setScopeOnView("(") }
        binding.buttonRight.setOnClickListener { setScopeOnView(")") }
        binding.editText.movementMethod = ScrollingMovementMethod()
        binding.solvedOperationText.movementMethod = ScrollingMovementMethod()
    }

    private fun setSquareOnView(str: String){
        when{
            binding.editText.text.takeLast(1).toString() == "=" -> {
                binding.editText.text = str
                binding.solvedOperationText.text = ""
            }
            binding.editText.text.length == 1 && binding.editText.text.toString() == "0" ->  binding.editText.text = str
            binding.editText.text.takeLast(1).toString() == ")" && binding.buttonSquareRoot.isPressed -> binding.editText.append("*sqrt(")
            binding.editText.text.takeLast(1).isDigitsOnly() && binding.buttonSquareRoot.isPressed -> binding.editText.append("*sqrt(")
            binding.editText.text.toString().takeLast(1).contains(arrayOfSigns) -> binding.editText.append(str)
        }
    }

    private fun setScopeOnView(str: String){
        when{
            binding.editText.text.takeLast(1).toString() == "=" -> {
                binding.editText.text = str
                binding.solvedOperationText.text = ""
            }
            binding.editText.text.length == 1 && binding.buttonLeft.isPressed -> {
                val temp = binding.editText.text.toString()
                binding.editText.text = "($temp"
            }
            binding.editText.text.length == 1 && binding.buttonRight.isPressed -> binding.buttonRight.isPressed = false
            binding.editText.text.takeLast(1).isDigitsOnly() && binding.buttonLeft.isPressed -> binding.editText.append("*(")
            binding.editText.text.takeLast(1).toString() == ")" && binding.buttonLeft.isPressed -> binding.editText.append("*(")
            binding.editText.text.takeLast(1).toString() == "(" && binding.buttonRight.isPressed -> {
                val temp = binding.editText.text.toString()
                binding.editText.text = "${temp}0)"
            }
            binding.editText.text.toString().takeLast(1).contains(arrayOfSigns) -> binding.editText.append(str)
        }
    }
    private fun setNumberOnView(str: String){
        when{
            binding.editText.text.length == 1 && binding.editText.text.toString() == "0" -> binding.editText.text = str
            binding.editText.text.takeLast(1).toString() == "=" -> {
                binding.editText.text = str
                binding.solvedOperationText.text = ""
            }
        else -> binding.editText.append(str)
        }
    }

    private fun setTextView(str: String){
        when{
            binding.editText.text.isNotEmpty() && binding.editText.text.takeLast(1).isDigitsOnly()
                    || binding.editText.text.takeLast(1).toString() == ")"
                    || binding.editText.text.takeLast(1).toString() == "("
                    && binding.editText.text.toString() != "0" -> binding.editText.append(str)
            binding.editText.text.length == 1 -> binding.editText.append(str)
            binding.editText.text.takeLast(1).toString().contains(arrayOfSigns) -> {
                var expression = binding.editText.text.toString()
                val operator = expression.takeLast(1)
                val newExpression = operator.replace(operator,str)
                expression = expression.substring(0,expression.length-1)
                binding.editText.text = "$expression$newExpression"
            }
            binding.editText.text.takeLast(1).toString() == "=" -> {
                binding.editText.text = str
                binding.solvedOperationText.text = ""
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
        val expression: String =
            binding.editText.text.toString().substring(0, binding.editText.text.length - 1)
        var result: Any = 0
        try {
            val exp = ExpressionBuilder(expression).build()
            result = exp.evaluate()

            val resultLong = result.toLong()
            if(result == resultLong.toDouble())
                binding.solvedOperationText.text = resultLong.toString()
            else
                binding.solvedOperationText.text = result.toString()
        }catch (e:Exception){
            binding.solvedOperationText.text = "Error expression!"
        }
    }

    private fun String.contains(arrayOfSigns: ArrayList<String>): Boolean {
        for (i in arrayOfSigns){
            if (i == "+" || i == "-" || i == "*" || i == "/" || i == "%" || i == "^") return true
        }
        return false
    }

}



