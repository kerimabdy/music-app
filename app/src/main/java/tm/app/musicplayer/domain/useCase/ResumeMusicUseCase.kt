package tm.app.musicplayer.domain.useCase

import tm.app.musicplayer.domain.service.MusicController

class ResumeMusicUseCase(
    private val musicController: MusicController
) {
    operator fun invoke() = musicController.resume()
}