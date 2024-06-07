package com.example.check_kotlin_saveable_remember

import android.os.Bundle
import android.os.UserManager
import android.util.Log
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var count by remember { mutableIntStateOf(0) }
                    val user = rememberUser(name = "Taro")

                    Column(modifier =  Modifier.padding(innerPadding)) {
                        Text(text = "Hello ${user.name}!")
                        Counter(count = count) {
                            count++
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Counter(count:Int, onClick: () -> Unit) {
    Text(text = "$count")
    Button(onClick = { onClick() }) {
        Text("Count Up")
    }
}

@Composable
fun rememberUser(
    name: String = "Taro",
    lambda1: () -> Boolean = {true},
):User = rememberSaveable(
        lambda1,
        name,
        saver = UserSaver,
){
    Log.i("rememberUser", "initialize User")
    User(
        name = name,
    )
}

data class User(
    val name: String = "",
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