package ca.gbc.comp3071.hodgson_mackenzie.assignment1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val hoursInput = findViewById<EditText>(R.id.hoursworked)
        val rateInput = findViewById<EditText>(R.id.hourlyrate)
        val taxInput = findViewById<EditText>(R.id.taxrate)

        //About Button
        val aboutButton = findViewById<Button>(R.id.aboutbutton)

        aboutButton.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        //Calculate Button
        val calculateButton = findViewById<Button>(R.id.calculatebutton)

        //Checks if all inputs are filled
        calculateButton.setOnClickListener {

            if(hoursInput.text.toString().isEmpty() ||
                rateInput.text.toString().isEmpty() ||
                taxInput.text.toString().isEmpty()) {

                Toast.makeText(this, "Enter All Fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val payResult = findViewById<TextView>(R.id.payResult)
            val overTimePayResult = findViewById<TextView>(R.id.overtimePayResult)
            val totalPayResult = findViewById<TextView>(R.id.totalPayResult)
            val taxResult = findViewById<TextView>(R.id.taxResult)
            val no_of_hours = hoursInput.text.toString().toDouble()
            val hourly_rate = rateInput.text.toString().toDouble()
            val tax_rate = taxInput.text.toString().toDouble()
            var pay: Double
            var overtime_pay: Double

            //Checks for overtime
            if (no_of_hours <= 40){
                overtime_pay = 0.0
                pay = no_of_hours * hourly_rate
            } else { //If there is overtime
                overtime_pay = (no_of_hours - 40) * hourly_rate * 1.5
                pay = hourly_rate * 40
            }

            //Final Calculations
            val total_pay: Double = pay + overtime_pay
            val tax: Double = pay * tax_rate //ONLY TAX ON NORMAL PAY

            //Displays the output
            payResult.text = "%.2f".format(pay)
            overTimePayResult.text = "%.2f".format(overtime_pay)
            totalPayResult.text = "%.2f".format(total_pay)
            taxResult.text = "%.2f".format(tax)

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

