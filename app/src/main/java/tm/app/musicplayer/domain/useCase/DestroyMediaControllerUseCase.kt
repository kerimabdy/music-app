package tm.app.musicplayer.domain.useCase

import tm.app.musicplayer.domain.service.MusicController

class DestroyMediaControllerUseCase(
    private val musicController: MusicController
) {
    operator fun invoke() {
        musicController.destroy()
    }
}