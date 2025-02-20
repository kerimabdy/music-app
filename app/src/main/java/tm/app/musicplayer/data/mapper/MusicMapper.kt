package tm.app.musicplayer.data.mapper

import tm.app.musicplayer.data.remote.dto.MusicResponse
import tm.app.musicplayer.domain.music.model.Music

fun MusicResponse.toDomain(): Music {
    return Music(id, createdAt, name, artist, songUrl, thumbnailUrl)
}