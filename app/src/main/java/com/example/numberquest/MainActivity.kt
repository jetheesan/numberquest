package com.example.numberquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.setValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.numberquest.ui.theme.NumberQuestTheme
import androidx.compose.runtime.saveable.rememberSaveable

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

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

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
    println(wizardNumber)
    var resultMessage by remember { mutableStateOf("") }

    println(wizardNumber)
    Column (
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        WizardContent(message = resultMessage)
        UserGuess{guess ->
            resultMessage = viewModel.checkGuess(guess, wizardNumber)
            println(resultMessage)

        }


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
        println(currentInput)
    } )
    Button(
        onClick = {submittedGuess(currentInput) }
    ){

        Text(
            text = "submit")

    }
}

@Composable
fun WizardContent(message: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = message,
        )

    }
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
fun GreetingPreview() {
    NumberQuestTheme {
        App()
    }
}