package tm.app.musicplayer.ui.musicscreen

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import tm.app.musicplayer.domain.useCase.PauseMusicUseCase
import tm.app.musicplayer.domain.useCase.ResumeMusicUseCase
import tm.app.musicplayer.domain.useCase.SeekMusicToPositionUseCase
import tm.app.musicplayer.domain.useCase.SkipToNextMusicUseCase
import tm.app.musicplayer.domain.useCase.SkipToPreviousMusicUseCase

class MusicViewModel constructor(
    private val pauseMusicUseCase: PauseMusicUseCase,
    private val resumeMusicUseCase: ResumeMusicUseCase,
    private val skipToNextMusicUseCase: SkipToNextMusicUseCase,
    private val skipToPreviousMusicUseCase: SkipToPreviousMusicUseCase,
    private val seekMusicToPositionUseCase: SeekMusicToPositionUseCase,
): ViewModel() {
    fun onEvent(event: MusicEvent) {
        when (event) {
            MusicEvent.PauseMusic -> pauseMusic()
            MusicEvent.ResumeMusic -> resumeMusic()
            is MusicEvent.SeekMusicToPosition -> seekToPosition(event.position)
            MusicEvent.SkipToNextMusic -> skipToNextMusic()
            MusicEvent.SkipToPreviousMusic -> skipToPreviousMusic()
        }
    }

    private fun pauseMusic() {
        pauseMusicUseCase()
    }

    private fun resumeMusic() {
        resumeMusicUseCase()
    }

    private fun skipToNextMusic() = skipToNextMusicUseCase {

    }

    private fun skipToPreviousMusic() = skipToPreviousMusicUseCase {

    }

    private fun seekToPosition(position: Long) {
        seekMusicToPositionUseCase(position)
    }

    fun calculateColorPalette(drawable: Bitmap, onFinish: (Color) -> Unit) {
        Palette.from(drawable).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}