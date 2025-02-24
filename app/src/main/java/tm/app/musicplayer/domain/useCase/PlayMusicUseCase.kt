package tm.app.musicplayer.domain.useCase

import tm.app.musicplayer.domain.service.MusicController

class PlayMusicUseCase(
    private val musicController: MusicController
) {
    operator fun invoke(mediaItemIndex: Int) {
        musicController.play(mediaItemIndex)
    }
}