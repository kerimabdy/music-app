package tm.app.musicplayer.data.repository

import kotlinx.coroutines.flow.Flow
import tm.app.musicplayer.data.mapper.toDomain
import tm.app.musicplayer.data.remote.MusicRemoteDatabase
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.model.Playlist
import tm.app.musicplayer.domain.repository.MusicRepository
import tm.app.musicplayer.other.Resource

class MusicRepositoryImpl(
    private val musicRemoteDatabase: MusicRemoteDatabase
): MusicRepository {
    override suspend fun getAllMusics(): Flow<Resource<List<Music>>> {
        return musicRemoteDatabase.getAllMusics()
    }

    override suspend fun getAllMusicPlaylists(): Flow<Resource<List<Playlist>>> {
        return musicRemoteDatabase.getAllMusicPlaylists()
    }

    override suspend fun getPlaylistWithMusics(id: Int): Flow<Resource<Playlist>> {
        return musicRemoteDatabase.getPlaylistWithMusics(id)
    }
}