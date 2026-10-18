package ca.gbc.comp3071.hodgson_mackenzie.assignment1

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val hoursInput = findViewById<EditText>(R.id.hoursworked)
        val rateInput = findViewById<EditText>(R.id.hourlyrate)
        val taxInput = findViewById<EditText>(R.id.taxrate)

        //ABOUT BUTTON -----------------------------------------------------------------------------
        val aboutButton = findViewById<Button>(R.id.aboutbutton)

        aboutButton.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        //CALCULATE BUTTON -------------------------------------------------------------------------
        val calculateButton = findViewById<Button>(R.id.calculatebutton)

        calculateButton.setOnClickListener {

            if(hoursInput.text.toString().isEmpty() ||
                rateInput.text.toString().isEmpty() ||
                taxInput.text.toString().isEmpty()) {

                Toast.makeText(this, "Enter All Fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val payResult = findViewById<TextView>(R.id.payResult)
            val overTimepayResult = findViewById<TextView>(R.id.overtimePayResult)
            val totalPayResult = findViewById<TextView>(R.id.totalPayResult)
            val taxResult = findViewById<TextView>(R.id.taxResult)
            val no_of_hours = hoursInput.text.toString().toDouble()
            val hourly_rate = rateInput.text.toString().toDouble()
            val tax_rate = taxInput.text.toString().toDouble()

            var pay = 0.0
            var overtime_pay = 0.0
            var total_pay = 0.0
            var tax = 0.0

            if (no_of_hours <= 40){
                overtime_pay = 0.0
                pay = no_of_hours * hourly_rate
            } else {
                overtime_pay = (no_of_hours - 40) * hourly_rate * 1.5
                pay = hourly_rate * 40
            }

            total_pay = pay + overtime_pay
            tax = pay * tax_rate

            payResult.text = "%.2f".format(pay)
            overTimepayResult.text = "%.2f".format(overtime_pay)
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

