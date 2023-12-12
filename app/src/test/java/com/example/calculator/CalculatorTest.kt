package com.example.calculator

import org.junit.Assert.assertEquals
import org.junit.Test
class CalculatorTest {
    @Test
    fun all_Clear_Test_Non_Empty_String(){
        val calculate = CalculatorActivity()
        val result = calculate.allClearAction("2+6+7/8")
        assertEquals("", result)
    }
    @Test
    fun all_Clear_Test_Empty_String(){
        val calculate = CalculatorActivity()
        val result = calculate.allClearAction("")
        assertEquals("", result)
    }
    @Test
    fun backspace_Clear_Test_Non_Empty_String(){
        val calculate = CalculatorActivity()
        val result = calculate.backSpaceAction("2+6+7/8")
        assertEquals("2+6+7/", result)
    }
    @Test
    fun backspace_Clear_Test_Empty_String(){
        val calculate = CalculatorActivity()
        val result = calculate.backSpaceAction("")
        assertEquals("", result)
    }
    @Test
    fun calculation_Test_Empty_String(){
        val calculate = CalculatorActivity()
        val result = calculate.calculateResults("")
        assertEquals("", result)
    }
    @Test
    fun calculation_Test_Addition(){
        val calculate = CalculatorActivity()
        val result = calculate.calculateResults("5+5")
        assertEquals("10.0", result)
    }
    @Test
    fun calculation_Test_Subtraction(){
        val calculate = CalculatorActivity()
        val result = calculate.calculateResults("5-5")
        assertEquals("0.0", result)
    }
    @Test
    fun calculation_Test_Empty_Multiplication(){
        val calculate = CalculatorActivity()
        val result = calculate.calculateResults("5*5")
        assertEquals("25.0", result)
    }
    @Test
    fun calculation_Test_Empty_Division(){
        val calculate = CalculatorActivity()
        val result = calculate.calculateResults("5/5")
        assertEquals("1.0", result)
    }
    @Test
    fun calculation_Test_Empty_Addition_And_Subtraction(){
        val calculate = CalculatorActivity()
        val result = calculate.calculateResults("5+5-5")
        assertEquals("5.0", result)
    }
    @Test
    fun calculation_Test_Addition_And_Multiplication(){
        val calculate = CalculatorActivity()
        val result = calculate.calculateResults("5+5*5")
        assertEquals("30.0", result)
    }
    @Test
    fun calculation_Test_Addition_And_Division(){
        val calculate = CalculatorActivity()
        val result = calculate.calculateResults("5+5/5")
        assertEquals("6.0", result)
    }
}