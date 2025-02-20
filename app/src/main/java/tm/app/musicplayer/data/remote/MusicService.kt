package tm.app.musicplayer.data.remote

import tm.app.musicplayer.data.remote.dto.MusicResponse

interface MusicService {
    suspend fun getMusic(): List<MusicResponse>
}