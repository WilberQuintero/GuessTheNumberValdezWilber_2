package valdez.wilber.guessthenumber_wilber

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var minValue = 0
    var maxValue = 100
    var num: Int = 0
    var won = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val guessings: TextView = findViewById(R.id.guessings)
        val down: Button = findViewById(R.id.down)
        val up: Button = findViewById(R.id.up)
        val generate: Button = findViewById(R.id.generate)
        val guessed: Button = findViewById(R.id.guessed)

        
       //Mtra, como lo tenia usted no me corria bien del
        // todo asi que agregue cosas de mas y asi si.
        
        
        guessings.setText("Toca en 'Generar' para empezar")
        guessed.visibility = View.GONE
        generate.setOnClickListener {
            resetValues()
            num = Random.nextInt(minValue, maxValue + 1)
            guessings.setText(num.toString())
            generate.visibility = View.INVISIBLE
            guessed.visibility = View.VISIBLE
            guessed.setText("¡Es mi número!")
            toggleButtons(true)
        }

        up.setOnClickListener {
            minValue = num + 1
            if (checkingLimits()) {
                num = Random.nextInt(minValue, maxValue + 1)
                guessings.setText(num.toString())
            } else {
                handleImpossibleGuess(guessings, guessed)
            }
        }

        down.setOnClickListener {

            maxValue = num - 1
            if (checkingLimits()) {
                num = Random.nextInt(minValue, maxValue + 1)
                guessings.setText(num.toString())
            } else {
                handleImpossibleGuess(guessings, guessed)
            }
        }

        guessed.setOnClickListener {
            if (!won) {
                guessings.setText("¡Adiviné, tu número es el " + num + "!")
                guessed.setText("Volver a jugar")
                won = true
                toggleButtons(false)
            } else {
                generate.visibility = View.VISIBLE
                guessings.setText("Toca en 'Generar' para empezar")
                guessed.visibility = View.GONE
                resetValues()
            }
        }

    }


    fun resetValues() {
        minValue = 0
        maxValue = 100
        num = 0
        won = false
    }

    fun checkingLimits(): Boolean {
        return minValue <= maxValue
    }

    private fun toggleButtons(enable: Boolean) {
        findViewById<Button>(R.id.up).isEnabled = enable
        findViewById<Button>(R.id.down).isEnabled = enable
    }

    private fun handleImpossibleGuess(guessings: TextView, guessed: Button) {
        guessings.setText("¡Ups! Parece que me has despistado o ya llegamos al límite. ¿Has mentido o tu número no está en el rango inicial?")
        won = true 
        guessed.setText("Volver a jugar")
        toggleButtons(false) 
    }
}