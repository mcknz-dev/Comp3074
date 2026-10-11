package ca.gbc.comp3071.hodgson_mackenzie.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colors
private val ScreenBackground = Color(0xFFFFF7FF)
private val NameColor = Color(0xFF625B71)
private val MessageColor = Color(0xFF29252F)
private val AvatarColor = Color.White
private val RobotColor = Color(0xFFD9D9D9)

// Message model
data class ChatMessage(
    val name: String,
    val message: String
)

// Messages shown in the list
private val messages = listOf(
    ChatMessage("Mackenzie", "Hi!"),
    ChatMessage("Jim", "How are you?"),
    ChatMessage("Joe", "Test..1..2...3"),
    ChatMessage("Joe", "I hate coding!!!"),
    ChatMessage("Joe", "Hi!"),
    ChatMessage("Jim", "How are you?"),
    ChatMessage("Joe", "Test..1..2...3"),
    ChatMessage("Joe", "I hate coding!!!"),
    ChatMessage("Joe", "Hi!"),
    ChatMessage("Jim", "How are you?"),
    ChatMessage("Joe", "Test..1..2...3"),
    ChatMessage("Joe", "I hate coding!!!")
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor =
            android.graphics.Color.rgb(255, 247, 255)

        window.navigationBarColor =
            android.graphics.Color.rgb(255, 247, 255)

        window.decorView.systemUiVisibility =
            android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                    android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = ScreenBackground
                ) {
                    MessageList()
                }
            }
        }
    }
}

@Composable
fun MessageList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 24.dp),

        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = 16.dp
        ),

        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        items(messages) { message ->
            MessageItem(message)
        }
    }
}

@Composable
fun MessageItem(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Circular avatar with red outline
        Surface(
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            color = AvatarColor,
            border = BorderStroke(2.dp, Color.Red)
        ) {
            Box(
                modifier = Modifier.padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                AndroidRobot()
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Name and message bubble
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = message.name,
                color = NameColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Surface(
                shape = RoundedCornerShape(22.dp),
                color = ScreenBackground,
                shadowElevation = 2.dp
            ) {
                Text(
                    text = message.message,
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                    color = MessageColor,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun AndroidRobot() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Antennae
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(9.dp)
                    .background(RobotColor)
            )

            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(9.dp)
                    .background(RobotColor)
            )
        }

        // Robot head
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(19.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 18.dp,
                        topEnd = 18.dp
                    )
                )
                .background(RobotColor),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(3.dp)
                        .background(Color.White, CircleShape)
                )

                Box(
                    modifier = Modifier
                        .size(3.dp)
                        .background(Color.White, CircleShape)
                )
            }
        }

        // Robot body
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(9.dp)
                .clip(
                    RoundedCornerShape(
                        bottomStart = 4.dp,
                        bottomEnd = 4.dp
                    )
                )
                .background(RobotColor)
        )
    }
}