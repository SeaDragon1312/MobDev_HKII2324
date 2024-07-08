package com.example.xaydungluoi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.xaydungluoi.data.DataSource
import com.example.xaydungluoi.data.DataSource.topics
import com.example.xaydungluoi.model.Topic
import com.example.xaydungluoi.ui.theme.XayDungLuoiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
                XayDungLuoiTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        CoursesGrid(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                top = 8.dp,
                                end = 8.dp
                            )
                        )
                    }
                }
            }
        }
    }

@Composable
fun CoursesGrid(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(DataSource.topics) { topic ->
            CoursesCard(topic)
        }
    }
}


@Composable
fun CoursesCard(topic: Topic, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row {
            Image(
                painter = painterResource(id = topic.courseImage),
                contentDescription = stringResource(id = topic.courseName),
                modifier = Modifier
                    .height(68.dp)
                    .width(68.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    text = stringResource(id = topic.courseName),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 16.dp,
                        bottom = 8.dp,
                        end = 16.dp
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_grain),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                    Text(
                        text = topic.numberOfCourse.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopicPreview() {
    XayDungLuoiTheme {
        CoursesCard(Topic(R.string.architecture, 58, R.drawable.architecture))
    }
}