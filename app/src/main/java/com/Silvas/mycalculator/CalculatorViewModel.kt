package com.Silvas.mycalculator

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Scriptable

class CalculatorViewModel : ViewModel(){

    private val _equationText = MutableLiveData("")
    val equationText : LiveData<String> = _equationText

    private  val _resultText = MutableLiveData("0")
    val resultText : LiveData<String> = _resultText

    fun onButtonClick(btn:String){
        _equationText.value?.let {
            if(btn == "AC"){
                _equationText.value = ""
                _resultText.value = "0"
                return
            }
            if(btn == "C"){
                if(it.isNotEmpty()) { // this way you can view element is empty or not
                    _equationText.value = it.substring(0,it.length-1)  // delete the last element
                }
                return
            }
            if(btn == "="){
                _equationText.value = _resultText.value
                return
            }

            _equationText.value = it+btn

            try {
                _resultText.value = calculatorResult(_equationText.value.toString())
            }catch (_ : Exception){}
        }
    }

    fun calculatorResult(equation:String):String{
        val context : org.mozilla.javascript.Context = org.mozilla.javascript.Context.enter()
        context.optimizationLevel = -1
        val scripttable : Scriptable = context.initStandardObjects()
        var finalResult = context.evaluateString(scripttable,equation,"Javascript",1,null).toString()
        if(finalResult.endsWith(".0")){
            finalResult = finalResult.replace(".0","")
        }
        return finalResult
    }
}