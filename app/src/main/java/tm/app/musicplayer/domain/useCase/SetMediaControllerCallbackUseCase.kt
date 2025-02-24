package tm.app.musicplayer.domain.useCase

import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.service.MusicController
import tm.app.musicplayer.other.PlayerState

class SetMediaControllerCallbackUseCase(
    private val musicController: MusicController
) {
    operator fun invoke(
        callback: (
                playerState: PlayerState,
                currentMusic: Music?,
                currentPosition: Long,
                totalDuration: Long,
                isShuffleEnabled: Boolean,
                isRepeatOneEnabled: Boolean
                ) -> Unit
    ) {
        musicController.mediaControllerCallback = callback
    }
}