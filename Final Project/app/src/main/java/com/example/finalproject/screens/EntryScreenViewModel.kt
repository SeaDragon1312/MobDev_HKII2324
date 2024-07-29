import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class EntryScreenViewModel : ViewModel() {
    private val _selectedGameType = mutableStateOf<GameType?>(null)
    val selectedGameType: State<GameType?> = _selectedGameType

    fun selectGameType(gameType: GameType) {
        _selectedGameType.value = gameType
    }
}

enum class GameType {
    CLASSIC,
    DUAL_ROW,
    INPUT_BOXES,
    CONNECTING_NUMBERS,
    TIMED_CHALLENGE
}