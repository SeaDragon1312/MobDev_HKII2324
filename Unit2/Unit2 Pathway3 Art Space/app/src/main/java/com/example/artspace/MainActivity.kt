package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme

data class Artwork(
    val imageResId: Int,
    val title: String,
    val artist: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                ArtSpaceApp()
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    var currentImageIndex by remember { mutableStateOf(0) }
    val artworks = listOf(
        Artwork(R.drawable.image1, stringResource(id = R.string.name1), stringResource(id = R.string.author1)),
        Artwork(R.drawable.image2, stringResource(id = R.string.name2), stringResource(id = R.string.author2)),
        Artwork(R.drawable.image3, stringResource(id = R.string.name3), stringResource(id = R.string.author3))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageCard(
            painter = painterResource(id = artworks[currentImageIndex].imageResId),
            modifier = Modifier.weight(0.7f) // Chiếm 70% chiều cao màn hình
        )
        Spacer(modifier = Modifier.height(16.dp))
        ArtworkDetails(
            title = artworks[currentImageIndex].title,
            artist = artworks[currentImageIndex].artist,
            modifier = Modifier.weight(0.2f) // Chiếm 20% chiều cao màn hình
        )
        Spacer(modifier = Modifier.height(16.dp))
        ButtonRow(
            onPreviousClick = {
                if (currentImageIndex > 0) {
                    currentImageIndex--
                }
            },
            onNextClick = {
                if (currentImageIndex < artworks.size - 1) {
                    currentImageIndex++
                }
            },
            modifier = Modifier.weight(0.1f) // Chiếm 10% chiều cao màn hình
        )
    }
}

@Composable
fun ImageCard(painter: Painter, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ArtworkDetails(title: String, artist: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = artist,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun ButtonRow(onPreviousClick: () -> Unit, onNextClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(onClick = onPreviousClick) {
            Text(text = "Previous")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = onNextClick) {
            Text(text = "Next")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}