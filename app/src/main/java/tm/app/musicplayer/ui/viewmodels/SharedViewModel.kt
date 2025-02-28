package tm.app.musicplayer.ui.viewmodels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tm.app.musicplayer.domain.useCase.DestroyMediaControllerUseCase
import tm.app.musicplayer.domain.useCase.GetCurrentMusicPositionUseCase
import tm.app.musicplayer.domain.useCase.SetMediaControllerCallbackUseCase
import tm.app.musicplayer.other.MusicControllerUiState
import tm.app.musicplayer.other.PlayerState

class SharedViewModel(
    private val setMediaControllerCallbackUseCase: SetMediaControllerCallbackUseCase,
    private val getCurrentMusicPositionUseCase: GetCurrentMusicPositionUseCase,
    private val destroyMediaControllerUseCase: DestroyMediaControllerUseCase,
): ViewModel() {

    var musicControllerUiState by mutableStateOf(MusicControllerUiState())
        private set

    init {
        setMediaControllerCallback()
    }

    private var positionUpdateJob: Job? = null

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

            // Start or stop the position updater based on state
            if (playerState == PlayerState.PLAYING && positionUpdateJob?.isActive != true) {
                positionUpdateJob = viewModelScope.launch {
                    while (true) {
                        delay(100L) // Smoother with 1s instead of 3s?
                        musicControllerUiState = musicControllerUiState.copy(
                            currentPosition = getCurrentMusicPositionUseCase()
                        )
                    }
                }
            } else if (playerState != PlayerState.PLAYING) {
                positionUpdateJob?.cancel()
            }
        }
    }

//        fun setBackgroundColor() {
//        viewModelScope.launch {
//            musicControllerUiState.currentMusic?.thumbnailUrl?.let {
//                val palette = createPaletteFromImageUrl(it)
//
//                palette?.let { p ->
//                    val vibrantColor = p.getVibrantColor(0)
//                    musicControllerUiState = musicControllerUiState.copy(backgroundColor = vibrantColor)
//                }
//            }
//        }
//    }

    fun destroyMediaController() {
        destroyMediaControllerUseCase()
    }
//
//
//    private fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()
//
//    private suspend fun createPaletteFromImageUrl(imageUrl: String): Palette? {
//        return withContext(Dispatchers.IO) {
//            val request = ImageRequest.Builder(application) //context is needed for coil
//                .data(imageUrl)
//                .allowHardware(false) // Important for Palette generation on some devices
//                .build()
//            val result = application.imageLoader.execute(request)
//            if (result is SuccessResult) {
//                createPaletteSync(result.image.toBitmap())
//            } else {
//                null
//            }
//        }
//    }
}