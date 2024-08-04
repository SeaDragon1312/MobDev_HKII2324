import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.R
import com.example.finalproject.ui.theme.FinalProjectTheme

//@Composable
//fun MainScreen() {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "entry") {
//        composable("entry") {
//            EntryScreen(navController)
//        }
//        composable("classic") {
//            ClassicModeScreen()
//        }
//        composable("dualRow") {
//            DualRowModeScreen()
//        }
//        composable("inputBoxes") {
//            InputBoxesModeScreen()
//        }
//        composable("connectingNumbers") {
//            ConnectingNumbersModeScreen()
//        }
//        composable("timedChallenge") {
//            TimedChallengeModeScreen()
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreen(navController: NavController, viewModel: EntryScreenViewModel = viewModel()) {
    Scaffold(
        topBar = {
            EntryScreenAppBar()
        },
        content = { paddingValues ->
            EntryScreenContent(navController, viewModel, paddingValues)
        }
    )
}

@Composable
fun EntryScreenContent(navController: NavController, viewModel: EntryScreenViewModel, paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Image(
            painter = painterResource(id = R.drawable.math),
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
            Text(
                text = "Choose a game type",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            GameType.values().forEach { gameType ->
                GameTypeButton(gameType, navController, viewModel)
            }
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
fun GameTypeButton(gameType: GameType, navController: NavController, viewModel: EntryScreenViewModel) {
    Card(
        onClick = {
            viewModel.selectGameType(gameType)
            when (gameType) {
                GameType.POKEMON -> {
                    navController.navigate("connectingNumbers")
                }
                GameType.HIDDEN_CARD -> {
                    navController.navigate("hiddenCard")
                }
                else -> {
                    navController.navigate("addNumber/${gameType.name}")
                }
            }
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(135, 206, 250),
            contentColor = Color.Black
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
                text = gameType.name.replace("_", " ")
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EntryScreenPreview() {
    FinalProjectTheme {
        val navController = rememberNavController()
        EntryScreen(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun EntryScreenContentPreview() {
    FinalProjectTheme {
        val navController = rememberNavController()
        EntryScreenContent(navController, EntryScreenViewModel(), PaddingValues())
    }
}

@Preview
@Composable
fun GameTypeButtonPreview() {
    FinalProjectTheme {
        val navController = rememberNavController()
        GameTypeButton(GameType.CLASSIC, navController, EntryScreenViewModel())
    }
}