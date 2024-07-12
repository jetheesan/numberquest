package com.example.numberquest
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    // wizard number generator
     val wizardNumber = (0..100).random()

    fun checkGuess( guess: String, wizardNumber: Int ) : String {
        val guessInt = guess.toIntOrNull()
        if (guessInt == null ) {
            return "You need to guess a whole number between 0 & 100 to pass"
        }  else {
            if (guessInt > 100 || guessInt < 0 )
                return "Remember you need to guess a number between 0 & 100"

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

