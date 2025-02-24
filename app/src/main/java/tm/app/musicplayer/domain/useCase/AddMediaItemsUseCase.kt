package tm.app.musicplayer.domain.useCase

import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.service.MusicController

class AddMediaItemsUseCase(
    private val musicController: MusicController
) {
    operator fun invoke(musics: List<Music>) {
        musicController.addMediaItems(musics)
    }
}