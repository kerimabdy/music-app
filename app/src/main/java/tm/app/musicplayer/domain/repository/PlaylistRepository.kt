package tm.app.musicplayer.domain.repository

import kotlinx.coroutines.flow.Flow
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.model.Playlist
import tm.app.musicplayer.other.Resource

interface PlaylistRepository {
    suspend fun getCuratedPlaylists(): Flow<Resource<List<Playlist>>>
}