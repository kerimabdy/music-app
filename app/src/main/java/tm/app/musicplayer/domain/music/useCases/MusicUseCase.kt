package tm.app.musicplayer.domain.music.useCases

import tm.app.musicplayer.domain.music.model.Music
import tm.app.musicplayer.domain.music.repository.MusicRepository

class GetMusicUseCase(
    private val musicRepository: MusicRepository
) {
    suspend fun invoke(): List<Music> {
        return musicRepository.getMusic()
    }
}