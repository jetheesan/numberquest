package com.example.numberquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.numberquest.ui.theme.NumberQuestTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
    Column (
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Start),
            text = "This is the game screen",
        )
        Button(onClick = onNextScreen) {
            Text("Go to result screen")
        }
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