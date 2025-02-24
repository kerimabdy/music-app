package tm.app.musicplayer.domain.repository

import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.other.Resource

interface MusicRepository {
    suspend fun getAllMusics(): Resource<List<Music>>
}