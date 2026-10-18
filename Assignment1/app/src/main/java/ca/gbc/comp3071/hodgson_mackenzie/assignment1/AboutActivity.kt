package ca.gbc.comp3071.hodgson_mackenzie.assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //Back Button
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}