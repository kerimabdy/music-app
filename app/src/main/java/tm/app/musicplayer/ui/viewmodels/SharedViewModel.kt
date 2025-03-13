package tm.app.musicplayer.ui.viewmodels


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import tm.app.musicplayer.domain.useCase.DestroyMediaControllerUseCase
import tm.app.musicplayer.domain.useCase.PauseMusicUseCase
import tm.app.musicplayer.domain.useCase.ResumeMusicUseCase
import tm.app.musicplayer.domain.useCase.SetMediaControllerCallbackUseCase
import tm.app.musicplayer.other.MusicControllerEvent
import tm.app.musicplayer.other.MusicControllerUiState

class SharedViewModel(
    private val setMediaControllerCallbackUseCase: SetMediaControllerCallbackUseCase,
    private val destroyMediaControllerUseCase: DestroyMediaControllerUseCase,
    private val resumeMusicUseCase: ResumeMusicUseCase,
    private val pauseMusicUseCase: PauseMusicUseCase
): ViewModel() {

    var musicControllerUiState = MutableStateFlow(MusicControllerUiState())
        private set

    init {
        setMediaControllerCallback()
    }

    fun onEvent(event: MusicControllerEvent) {
        when (event) {
            is MusicControllerEvent.UpdatePosition -> updateCurrentPosition(event.newPosition)
            is MusicControllerEvent.PlayPauseToggle -> playPauseToggle(event.isPlaying)
        }
    }

    private fun setMediaControllerCallback() {
        setMediaControllerCallbackUseCase { playerState, currentSong, currentPosition, totalDuration,
                                            isShuffleEnabled, isRepeatOneEnabled ->
            musicControllerUiState.update {
                it.copy(
                    playerState = playerState,
                    currentMusic = currentSong,
                    currentPosition = currentPosition,
                    totalDuration = totalDuration,
                    isShuffleEnabled = isShuffleEnabled,
                    isRepeatOneEnabled = isRepeatOneEnabled
                )
            }
        }
    }

    private fun playPauseToggle(isPlaying: Boolean) {
        if (isPlaying) {
            pauseMusicUseCase()
        } else {
            resumeMusicUseCase()
        }
    }

    private fun updateCurrentPosition(newPosition: Long) {
        musicControllerUiState.update {
            it.copy(
                currentPosition = newPosition
            )
        }
    }

    fun destroyMediaController() {
        destroyMediaControllerUseCase()
    }

}