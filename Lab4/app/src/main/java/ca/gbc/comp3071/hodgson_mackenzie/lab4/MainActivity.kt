package ca.gbc.comp3071.hodgson_mackenzie.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.gbc.comp3071.hodgson_mackenzie.lab4.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)

        setContent {
            Lab4Theme {
                val customerViewModel: CustomerViewModel = viewModel(
                    factory = CustomerViewModelFactory(
                        database.customerDao()
                    )
                )

                CustomerApp(customerViewModel)
            }
        }
    }
}

