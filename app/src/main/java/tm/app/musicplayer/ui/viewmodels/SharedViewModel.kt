package tm.app.musicplayer.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tm.app.musicplayer.domain.useCase.DestroyMediaControllerUseCase
import tm.app.musicplayer.domain.useCase.GetCurrentMusicPositionUseCase
import tm.app.musicplayer.domain.useCase.SetMediaControllerCallbackUseCase
import tm.app.musicplayer.other.MusicControllerUiState
import tm.app.musicplayer.other.PlayerState
import kotlin.time.Duration.Companion.seconds

class SharedViewModel(
    private val setMediaControllerCallbackUseCase: SetMediaControllerCallbackUseCase,
    private val getCurrentMusicPositionUseCase: GetCurrentMusicPositionUseCase,
    private val destroyMediaControllerUseCase: DestroyMediaControllerUseCase
): ViewModel() {
    var musicControllerUiState by mutableStateOf(MusicControllerUiState())
        private set

    init {
        setMediaControllerCallback()
    }

    private fun setMediaControllerCallback() {
        setMediaControllerCallbackUseCase { playerState, currentSong, currentPosition, totalDuration,
                                            isShuffleEnabled, isRepeatOneEnabled ->
            musicControllerUiState = musicControllerUiState.copy(
                playerState = playerState,
                currentMusic = currentSong,
                currentPosition = currentPosition,
                totalDuration = totalDuration,
                isShuffleEnabled = isShuffleEnabled,
                isRepeatOneEnabled = isRepeatOneEnabled
            )

            if (playerState == PlayerState.PLAYING) {
                viewModelScope.launch {
                    while (true) {
                        delay(3.seconds)
                        musicControllerUiState = musicControllerUiState.copy(
                            currentPosition = getCurrentMusicPositionUseCase()
                        )
                    }
                }
            }
        }
    }

    fun destroyMediaController() {
        destroyMediaControllerUseCase()
    }
}