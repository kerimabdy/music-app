package tm.app.musicplayer.data.remote

import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.other.Resource

interface MusicRemoteDatabase {
    suspend fun getAllMusics(): Resource<List<Music>>
}