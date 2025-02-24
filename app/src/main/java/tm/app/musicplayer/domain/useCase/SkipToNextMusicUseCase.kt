package tm.app.musicplayer.domain.useCase

import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.service.MusicController

class SkipToNextMusicUseCase(
    private val musicController: MusicController
) {
    operator fun invoke(updateHomeUi: (Music?) -> Unit) {
        musicController.skipToNextMusic()
        updateHomeUi(musicController.getCurrentMusic())
    }
}