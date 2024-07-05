package com.example.clickbehavior

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clickbehavior.ui.theme.ClickBehaviorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClickBehaviorTheme {
                LemonApp()
            }
        }
    }
}

@Composable
fun LemonApp(modifier : Modifier = Modifier) {
    var result by remember {
        mutableStateOf(1)
    }
    val imageResource = when (result) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val descriptionResource = when (result) {
        1 -> R.string.step_1
        2 -> R.string.step_2
        3 -> R.string.step_3
        else -> R.string.step_4
    }
    Box(modifier = modifier) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                if (result == 2){
                    var ran = (2..4).random()
                    if(ran == 2) {
                        result++
                    }
                }
                else if (result < 4) {
                    result++
                } else {
                    result = 1
                }
            }) {
                Image(painter = painterResource(imageResource), contentDescription = null)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(descriptionResource),
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonAppPreview() {
    ClickBehaviorTheme {
        LemonApp(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
        )
    }
}