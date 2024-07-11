package com.example.numberquest

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    // wizard number generator
     val wizardNumber = (0..100).random()

    fun checkGuess( guess: String, wizardNumber: Int ) : Pair<String, Color> {
        val guessInt = guess.toIntOrNull()
        if (guessInt == null ) {
            return "You need to guess a whole number between 0 & 100 to pass" to Color.Red
        }  else {
            if (guessInt > 100 || guessInt < 0 )
                return "Remember you need to guess a number between 0 & 100" to Color.Red

            return if (guessInt > wizardNumber ) {
                "lower" to Color.Magenta
            } else if (guessInt < wizardNumber) {
                "higher" to Color.Blue
            } else  {
                return "correct, you may pass" to Color.Green
            }
        }
    }

}

