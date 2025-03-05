package tm.app.musicplayer.domain.useCase

import tm.app.musicplayer.domain.model.Playlist
import tm.app.musicplayer.domain.repository.PlaylistRepository
import tm.app.musicplayer.other.Resource

class GetCuratedPlaylistsUseCase(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(): Resource<List<Playlist>> {
        return playlistRepository.getCuratedPlaylists()
    }
}