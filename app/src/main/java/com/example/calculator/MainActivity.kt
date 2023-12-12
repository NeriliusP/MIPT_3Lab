package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private var canAddOperation = false
    private var canAddDecimal = true
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @SuppressLint("SuspiciousIndentation")
    fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                if (canAddDecimal)
                    binding.displayView.append(view.text)

                    canAddDecimal = false
                } else
                    binding.displayView.append(view.text)

                canAddOperation = true
            }
        }
    fun operatorAction(view: View)
    {
        if(view is Button && canAddOperation && view.text!="±")
        {
            binding.displayView.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
        else if(view is Button && view.text=="√")
        {
            binding.displayView.append(view.text)
            canAddOperation = false
            canAddDecimal = false
        }
        else if(view is Button && view.text=="±")
        {
            if(binding.displayView.text.startsWith("-"))
            {
                binding.displayView.text=binding.displayView.text.substring(1)
            }
            else
            {
                binding.displayView.text="-"+binding.displayView.text
            }
        }
    }
    fun equalsAction(view: View)
    {
        binding.displayView.text = calculateResults()
    }
    fun allClearAction(view: View)
    {
        binding.displayView.text=""
    }
    fun backSpaceAction(view: View)
    {
        if (binding.displayView.text.length>1)
        {
            binding.displayView.text = binding.displayView.text.substring(0, binding.displayView.text.length-1)
        }
        else
        {
            binding.displayView.text=""
        }

    }
    private fun calculateResults(): String
    {
        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitsOperators)
        if(timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float
    {
        var result = passedList[0] as Float

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }

        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while (list.contains('√'))
        {
            list=calcSqrRoot(list)
        }
        while (list.contains('*') || list.contains('/'))
        {
            list = calcTimesDiv(list)
        }
        return list
    }

    private fun calcSqrRoot(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when(operator)
                {
                    '√' ->
                    {
                        newList.add(sqrt(nextDigit))
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if(i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when(operator)
                {
                    '*' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if(i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }

    private fun digitsOperators(): MutableList<Any>
    {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for(character in binding.displayView.text)
        {
            if(character.isDigit() || character == '.')
                currentDigit += character
            else
            {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }

        if(currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
    }

}