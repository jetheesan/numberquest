package com.example.numberquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.numberquest.ui.theme.NumberQuestTheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NumberQuestTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),

                ) {
                    App()
                }

            }
        }
    }
}


@Composable()
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "gamescreen") {
        composable(route = "gamescreen") {
            GameScreen(onNextScreen = {
                navController.navigate("resultscreen")
            })
        }
        composable(route = "resultscreen") {
            ResultScreen()
        }
    }
}

@Composable()
fun GameScreen(onNextScreen: () -> Unit) {
    val viewModel: GameViewModel = viewModel()
    val wizardNumber = viewModel.wizardNumber
    println("This is gamescreen: $wizardNumber")
    var resultMessage by remember { mutableStateOf("Take a guess" to Color.Black) }
    var guessCounter by remember { mutableIntStateOf(0) }

    Column (
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        WizardContent(message = resultMessage.first, color = resultMessage.second )
        UserGuess { guess ->
            resultMessage = viewModel.checkGuess(guess, wizardNumber)
            guessCounter++
            println("Results: $resultMessage")}

            Text(text = "Guess Count")
            Counter(count = guessCounter)
            WizardImage()
        }
    }



@Composable
fun UserGuess(submittedGuess: (String) -> Unit){
    var currentInput by remember {
        mutableStateOf("") // currentGuess is defined as empty string
    }
//    var currentGuess: Int? = currentInput.
    TextField(
        value = currentInput,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { // as the text field is updated with string, it updates the string currentGuess
        newValue ->
        currentInput = newValue
        println("current input: $currentInput")
    } )
    Button(
        onClick = {submittedGuess(currentInput) }
    ){

        Text(
            text = "submit")

    }
}

@Composable
fun WizardContent(message: String, color: Color) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = message,
            color = color
        )

    }
}

@Composable
fun Counter (count:Int){
    Box(modifier = Modifier
        .size(48.dp)
        .background(Color.Black, shape = CircleShape),
        contentAlignment = Alignment.Center)

    {Text(text=count.toString(), color = Color.White)}





}

@Composable
fun WizardImage(){
Image(
painter = painterResource(id = R.drawable.wizard),
contentDescription = "Wizard Image" )
}

@Composable
fun ResultScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,

    ) {
       Text( text = "This is the result screen")
    }
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    NumberQuestTheme {
        App()
    }
}