package ca.gbc.comp3071.hodgson_mackenzie.lab2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var output = 0
    private var step = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val outputText = findViewById<TextView>(R.id.textViewOutput)

        val addButton = findViewById<Button>(R.id.button6)
        val subtractButton = findViewById<Button>(R.id.button5)
        val resetButton = findViewById<Button>(R.id.button3)
        val stepButton = findViewById<Button>(R.id.button4)

        addButton.setOnClickListener {
            output += step
            outputText.text = output.toString()
        }

        subtractButton.setOnClickListener {
            output -= step
            outputText.text = output.toString()
        }

        resetButton.setOnClickListener {
            output = 0
            step = 1
            outputText.text = output.toString()
        }

        stepButton.setOnClickListener {
            step = if (step == 1) 2 else 1
        }
    }
}