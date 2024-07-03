package com.example.pathway3ex3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pathway3ex3.ui.theme.Pathway3ex3Theme
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pathway3ex3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Compose4Conner()
                }
            }
        }
    }
}

@Composable
fun Compose4Conner() {
    Row(Modifier.fillMaxSize()){
        Column(Modifier.weight(1f)) {
            TextOfCompose(
                title = stringResource(R.string.title1),
                description = stringResource(R.string.description1),
                backgroundColor = Color(0xFFEADDFF),
                modifier = Modifier.weight(1f)
            )
            TextOfCompose(
                title = stringResource(R.string.title2),
                description = stringResource(R.string.description2),
                backgroundColor = Color(0xFFB69DF8),
                modifier = Modifier.weight(1f)
            )
        }
        Column(Modifier.weight(1f)) {
            TextOfCompose(
                title = stringResource(R.string.title3),
                description = stringResource(R.string.description3),
                backgroundColor = Color(0xFFD0BCFF),
                modifier = Modifier.weight(1f)
            )
            TextOfCompose(
                title = stringResource(R.string.title4),
                description = stringResource(R.string.description4),
                backgroundColor = Color(0xFFF6EDFF),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TextOfCompose(
    title : String,
    description: String,
    backgroundColor : androidx.compose.ui.graphics.Color,
    modifier : Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().background(backgroundColor).padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            textAlign = TextAlign.Justify
        )
    }
}


@Preview(showBackground = true)
@Composable
fun Compose4ConnerPreview() {
    Pathway3ex3Theme {
        Compose4Conner()
    }
}