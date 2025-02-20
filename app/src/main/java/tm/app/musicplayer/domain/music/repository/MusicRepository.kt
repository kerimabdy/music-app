package tm.app.musicplayer.domain.music.repository

import tm.app.musicplayer.domain.music.model.Music

interface MusicRepository {
    suspend fun getAllMusics(): List<Music>
}