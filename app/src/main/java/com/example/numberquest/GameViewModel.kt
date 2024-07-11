package com.example.numberquest

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    // wizard number generator
     val wizardNumber = (0..100).random()

    fun checkGuess( guess: String, wizardNumber: Int ) :String {
        val guessInt = guess.toIntOrNull()
        if (guessInt == null ) {
            return "not a number!"
        } else  {
            return if (guessInt > wizardNumber ) {
                "lower"
            } else if (guessInt < wizardNumber) {
                "higher"
            } else  {
                return "correct, you may pass"
            }
        }
    }

}

