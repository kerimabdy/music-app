package tm.app.musicplayer.domain.useCase

import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.service.MusicDownloadController
import tm.app.musicplayer.other.Resource

class DownloadMusicUseCase(
    private val downloadController: MusicDownloadController
) {
    suspend operator fun invoke(music: Music): Resource<Unit> {
        return downloadController.downloadMusic(music)
    }
}