package com.example.check_kotlin_saveable_remember

import android.os.Bundle
import android.os.UserManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.check_kotlin_saveable_remember.ui.theme.Check_Kotlin_Saveable_RememberTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Check_Kotlin_Saveable_RememberTheme {
                val user = rememberWrapper()
                var count by remember{
                    mutableIntStateOf(0)
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Greeting(
                            user = user,
                            onClick = {count += 1},
                            modifier = Modifier.padding(innerPadding)
                        )
                        CountText(
                            count = count
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(user: User,
             modifier: Modifier = Modifier,
             onClick: () -> Unit = {},) {
    Column {
        Text(
            text = "Hello ${user.name}!",
            modifier = modifier
        )

        Button(onClick = { onClick() }) {
            Text("Change name")
        }
    }
}

@Composable
fun CountText(
    count: Int = 0,
    modifier: Modifier = Modifier,
){
    Text(
        text = "Count: $count",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Check_Kotlin_Saveable_RememberTheme {
        Greeting(User("Android"))
    }
}

@Composable
fun rememberWrapper(
    lambda1: () -> Boolean = {true},
    lambda2: () -> Boolean = {false},
):User{
    return rememberUser(lambda1, lambda2)
}

@Composable
fun rememberUser(
    lambda1: () -> Boolean = {true},
    lambda2: () -> Boolean = {false},
):User = rememberSaveable(
        lambda1,
        lambda2,
        saver = UserSaver,
){
    User()
}

data class User(
    val name: String = ""
)

val UserSaver = Saver<User, String>(
    save = {
        // Bundleに保存できる型に変換
        it.name
    },
    restore = {
        // Bundleから復元した値を変換
        User(it)
    }
)