package tm.app.musicplayer.domain.repository

import kotlinx.coroutines.flow.Flow
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.model.Playlist
import tm.app.musicplayer.other.Resource

interface MusicRepository {
    suspend fun getAllMusics(): Flow<Resource<List<Music>>>
    suspend fun getAllMusicPlaylists(): Flow<Resource<List<Playlist>>>
    suspend fun getPlaylistWithMusics(id: Int): Flow<Resource<Playlist>>
}