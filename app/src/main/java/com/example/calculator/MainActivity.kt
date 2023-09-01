package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        Log.i("Test","$number")
        binding.buttonClearAll.setOnClickListener { clearAll() }
        binding.buttonClear.setOnClickListener { clearBack() }
        binding.button0.setOnClickListener { setTextView("0") }
        binding.button1.setOnClickListener { setTextView("1") }
        binding.button2.setOnClickListener { setTextView("2") }
        binding.button3.setOnClickListener { setTextView("3") }
        binding.button4.setOnClickListener { setTextView("4") }
        binding.button5.setOnClickListener { setTextView("5") }
        binding.button6.setOnClickListener { setTextView("6") }
        binding.button7.setOnClickListener { setTextView("7") }
        binding.button8.setOnClickListener { setTextView("8") }
        binding.button9.setOnClickListener { setTextView("9") }
        binding.buttonDelimiter.setOnClickListener { setTextView(".") }
        binding.buttonAddition.setOnClickListener { setTextView("+") }
        binding.buttonDivision.setOnClickListener { setTextView("/") }
        binding.buttonSubtraction.setOnClickListener { setTextView("-") }
        binding.buttonMultiplication.setOnClickListener { setTextView("×") }
        binding.buttonSolve.setOnClickListener {
        solveOperation()}
        binding.buttonPercent.setOnClickListener { setTextView("%") }
        binding.buttonFactorial.setOnClickListener { setTextView("!") }
        binding.buttonExponentation.setOnClickListener { setTextView("^") }
        binding.buttonPi.setOnClickListener { setTextView("3.14") }
    }

    private fun setTextView(str: String){
        if (binding.editText.text.length == 1 && binding.editText.text.toString() == "0"){
            binding.editText.text = str
        }else{
            binding.editText.append(str)
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

    private fun solveOperation(){
        var expression: String = binding.editText.text.toString()
        val exp = ExpressionBuilder(expression).build()
        val result = exp.evaluate()
        binding.solvedOperationText.text = result.toString()
    }
}

