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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.R
import com.example.finalproject.screens.ClassicModeScreen
import com.example.finalproject.screens.ConnectingNumbersModeScreen
import com.example.finalproject.screens.DualRowModeScreen
import com.example.finalproject.screens.InputBoxesModeScreen
import com.example.finalproject.screens.TimedChallengeModeScreen
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
                GameType.CLASSIC -> navController.navigate("classic")
                GameType.DUAL_ROW -> navController.navigate("dualRow")
                GameType.INPUT_BOXES -> navController.navigate("inputBoxes")
                GameType.CONNECTING_NUMBERS -> navController.navigate("connectingNumbers")
                GameType.TIMED_CHALLENGE -> navController.navigate("timedChallenge")
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
        EntryScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun EntryScreenContentPreview() {
    FinalProjectTheme {
        EntryScreenContent(rememberNavController(), EntryScreenViewModel(), PaddingValues())
    }
}

@Preview
@Composable
fun GameTypeButtonPreview() {
    FinalProjectTheme {
        GameTypeButton(GameType.CLASSIC, rememberNavController(), EntryScreenViewModel())
    }
}