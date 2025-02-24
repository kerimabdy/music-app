package tm.app.musicplayer.domain.useCase

import tm.app.musicplayer.domain.service.MusicController

class SeekMusicToPositionUseCase(
    private val musicController: MusicController
) {
    operator fun invoke(position: Long) {
        musicController.seekTo(position)
    }
}