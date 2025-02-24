package tm.app.musicplayer.domain.useCase

import tm.app.musicplayer.domain.service.MusicController

class PauseMusicUseCase(
    private val musicController: MusicController
) {
    operator fun invoke() = musicController.pause()
}