package com.example.businesscardapp

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscardapp.ui.theme.BusinessCardAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BusinessCard()
                }
            }
        }
    }
}

@Composable
fun BusinessCard(modifier: Modifier = Modifier) {
    PersonalInfo(name = "Hoang Hai Long", title = "UET Student")
    ContactInfo()
}

@Composable
fun PersonalInfo(name : String, title : String) {
    val logoImage = painterResource(R.drawable.android_logo)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(143, 188, 143))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = logoImage,
            contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .padding(8.dp)
                .background(Color(0xFF003153)))
        Text(
            text = name,
            fontSize = 36.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0, 100, 0),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ContactInfo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        ContactInfoRow(
            iconId = R.drawable.call_24dp,
            info = stringResource(R.string.phoneNumber)
        )
        ContactInfoRow(iconId = R.drawable.share_24dp, stringResource(R.string.androiddev))
        ContactInfoRow(iconId = R.drawable.email_24dp, info = stringResource(R.string.email))
    }
}

@Composable
fun ContactInfoRow(iconId: Int, info: String) {
    Row(
        modifier = Modifier.padding(start = 80.dp, bottom = 8.dp)
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = info,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    BusinessCardAppTheme {
        BusinessCard()
    }
}