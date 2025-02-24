package tm.app.musicplayer.data.repository

import tm.app.musicplayer.data.mapper.toDomain
import tm.app.musicplayer.data.remote.MusicRemoteDatabase
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.repository.MusicRepository
import tm.app.musicplayer.other.Resource

class MusicRepositoryImpl(
    private val musicRemoteDatabase: MusicRemoteDatabase
): MusicRepository {
    override suspend fun getAllMusics(): Resource<List<Music>> {
        return musicRemoteDatabase.getAllMusics()
    }
}