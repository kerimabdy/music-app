package tm.app.musicplayer.data.remote

import kotlinx.coroutines.flow.Flow
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.model.Playlist
import tm.app.musicplayer.other.Resource

interface MusicRemoteDatabase {
    suspend fun getAllMusics(): Resource<List<Music>>
    suspend fun getAllPlaylist(): Flow<Resource<List<Playlist>>>
}