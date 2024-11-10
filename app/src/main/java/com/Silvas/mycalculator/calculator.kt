package com.Silvas.mycalculator

import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import java.time.format.TextStyle

val buttonList = listOf(
    "C","(",")","/",
    "7","8","9","*",
    "4","5","6","+",
    "1","2","3","-",
    "AC","0",".","="
)


@Composable
fun Calculator (modifier: Modifier = Modifier,viewModel: CalculatorViewModel){ // calling the class Calculator view model

    val equationText = viewModel.equationText.observeAsState()
    val resultText = viewModel.resultText.observeAsState()

    Box(modifier = modifier){
        Column(
            modifier=modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End

        ) {
            Text(
                text = equationText.value?:"",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 30.sp,
                    textAlign = TextAlign.End
                ),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            ) // adding text fileds

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = resultText.value?:"",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 60.sp,
                    textAlign = TextAlign.End
                ),
                maxLines = 5,
            )
            
            Spacer(modifier = Modifier.height(10.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4)){
                items(buttonList){
                    CalculatorButton(btn = it, onClick = {
                        viewModel.onButtonClick(it)
                    })
                }
            }

        }
    }
}

@Composable
fun CalculatorButton(btn:String, onClick : ()-> Unit) {
    Box(modifier = Modifier.padding(8.dp)){
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            contentColor = Color.Black,
            backgroundColor = getColor(btn)



        ) {
            Text(text = btn , fontSize = 25.sp , fontWeight = FontWeight.Bold)
        }
    }
}

fun getColor(btn:String) : Color{
    if(btn == "=")
        return Color(0xFFFFC107)
    if(btn == "C" || btn == "AC")
        return Color(0xFF56ADFF)
    if(btn == "(" || btn == ")" || btn == ".")
        return Color(0xFFBEBEBE)
    if(btn == "*" || btn == "/" || btn == "+" || btn == "-")
        return Color(0xFFFF6C46)
    return Color(0xFFFFFFFF)
}