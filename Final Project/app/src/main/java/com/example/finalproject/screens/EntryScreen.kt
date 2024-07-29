package com.example.finalproject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.R
import com.example.finalproject.ui.theme.FinalProjectTheme

@Composable
fun EntryScreen() {
    Scaffold(
        topBar = {
            EntryScreenAppBar()
        },
        content = { paddingValues ->
            EntryScreenContent(paddingValues = paddingValues)
        }
    )
}

@Composable
fun EntryScreenContent(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Image(
            painter = painterResource(id = R.drawable.math), // Đặt ID của ảnh ở đây
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GameTypeButton("Classic Mode")
            GameTypeButton("Dual-Row Mode")
            GameTypeButton("Input Boxes Mode")
            GameTypeButton("Connecting Numbers Mode")
            GameTypeButton("Timed Challenge Mode")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreenAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTypeButton(typeMode: String) {
    Card(
        onClick = { /*TODO*/ },
        colors = CardDefaults.cardColors(
            containerColor = Color(135,206,250), // Màu nền trắng cho button
            contentColor = Color.Black // Màu văn bản đen cho tương phản
        ),
        modifier = Modifier
            .padding(8.dp)
            .size(height = 60.dp, width = 200.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = typeMode
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EntryScreenPreview() {
    FinalProjectTheme {
        EntryScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun EntryScreenContentPreview() {
    FinalProjectTheme {
        EntryScreenContent(paddingValues = PaddingValues())
    }
}

@Preview
@Composable
fun GameTypeButtonPreview() {
    FinalProjectTheme {
        GameTypeButton("Duong Ngu")
    }
}
