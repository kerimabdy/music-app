package tm.app.musicplayer.data.repository

import tm.app.musicplayer.data.mapper.toDomain
import tm.app.musicplayer.data.remote.MusicService
import tm.app.musicplayer.domain.music.model.Music
import tm.app.musicplayer.domain.music.repository.MusicRepository

class MusicRepositoryImpl(
    private val musicService: MusicService
): MusicRepository {
    override suspend fun getMusic(): List<Music> {
        return musicService.getMusic().map { it.toDomain() }
    }
}