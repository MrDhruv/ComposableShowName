package com.dhruv.displayname

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dhruv.displayname.ui.theme.DisplayNameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisplayName ("Dhruv")
        }
    }
}

@Composable
fun DisplayName(name: String) {
    Text(text = name)
}

@Preview(showBackground = true)
@Composable
fun PreviewUI() {
    DisplayName ("Dhruv")
}