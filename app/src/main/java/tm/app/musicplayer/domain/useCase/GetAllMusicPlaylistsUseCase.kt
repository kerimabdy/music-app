package tm.app.musicplayer.domain.useCase

import tm.app.musicplayer.domain.repository.MusicRepository

class GetAllMusicPlaylistsUseCase(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke() = musicRepository.getAllMusicPlaylists()
}