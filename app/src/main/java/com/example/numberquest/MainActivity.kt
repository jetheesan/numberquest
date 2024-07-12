package com.example.numberquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlin.reflect.typeOf
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
    NavHost(navController = navController, startDestination = "homescreen") {
    composable(route = "homescreen") {
        HomeScreen(onStartGame = {
            navController.navigate("gamescreen")
    })

    }

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

@Composable
fun HomeScreen(onStartGame: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 0.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = " 🪄NUMBER QUEST 🪄",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 35. sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onStartGame) {
                Text(
                    text = "Start Game",
                )

            }
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 0.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            WizardImage(modifier = Modifier.size(400.dp))
        }

    }
}

@Composable()
fun GameScreen(onNextScreen: () -> Unit) {
    val viewModel: GameViewModel = viewModel()
    val wizardNumber = viewModel.wizardNumber
    var guessCounter by remember { mutableIntStateOf(0) }
    val wizardMessages = remember { mutableStateListOf<String>("I've thought of a number between 1 and 100", "Can you read my mind?") }
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        WizardContent(messages = wizardMessages)
        println("current wizard messages: $wizardMessages")
        println("adding new messages")
        println("current wizard messages")
        UserGuess { guess ->
            wizardMessages.add("You guessed $guess")
            wizardMessages.add(viewModel.checkGuess(guess, wizardNumber))
            if (wizardMessages.size > 2) {
                wizardMessages.removeAt(0)
                wizardMessages.removeAt(0)
            }
            println("check guess result: ${viewModel.checkGuess(guess, wizardNumber)}")
            guessCounter++
            println("wizard messages: $wizardMessages")
            for (i in 0..<wizardMessages.size) {
                val message = wizardMessages[i]
                println("wizardMessage $message")

            }

        }
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
    } )
    Button(
        onClick = {submittedGuess(currentInput) }
    ){

        Text(
            text = "submit")

    }
}

@Composable
fun WizardContent(messages: List<String>) {

    Column(modifier = Modifier
        .padding(16.dp))
        {
        for (message in messages) {
            Box(modifier = Modifier
                .padding(all = 20.dp)
                .fillMaxWidth()
                .background(
                    color = Color.Yellow,
                    shape = RoundedCornerShape(10.dp)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message,
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
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
fun WizardImage(modifier: Modifier = Modifier){
Image(
    painter = painterResource(id = R.drawable.wizard),
    contentDescription = "Wizard Image" ,
    modifier = modifier)
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