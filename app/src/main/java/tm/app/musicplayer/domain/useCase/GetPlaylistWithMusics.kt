package tm.app.musicplayer.domain.useCase

import tm.app.musicplayer.domain.repository.MusicRepository

class GetPlaylistWithMusics(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(id: Int) = musicRepository.getPlaylistWithMusics(id)
}