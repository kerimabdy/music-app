package tm.app.musicplayer.data.repository

import kotlinx.coroutines.flow.Flow
import tm.app.musicplayer.data.remote.MusicRemoteDatabase
import tm.app.musicplayer.domain.model.Playlist
import tm.app.musicplayer.domain.repository.PlaylistRepository
import tm.app.musicplayer.other.Resource

class PlaylistRepositoryImpl(
    private val musicRemoteDatabase: MusicRemoteDatabase
): PlaylistRepository {
    override suspend fun getCuratedPlaylists(): Flow<Resource<List<Playlist>>> {
        return musicRemoteDatabase.getAllPlaylist()
    }
}