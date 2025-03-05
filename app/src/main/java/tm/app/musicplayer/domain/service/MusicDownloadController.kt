package tm.app.musicplayer.domain.service

import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.other.Resource

interface MusicDownloadController {
    suspend fun downloadMusic(music: Music): Resource<Unit>
    suspend fun getDownloadedMusic(): Resource<List<Music>>
    fun cancelDownload(musicId: Int)
}