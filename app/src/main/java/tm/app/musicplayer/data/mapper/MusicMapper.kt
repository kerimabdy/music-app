package tm.app.musicplayer.data.mapper

import androidx.media3.common.MediaItem
import tm.app.musicplayer.data.remote.dto.MusicResponse
import tm.app.musicplayer.domain.model.Music

fun MusicResponse.toDomain(): Music {
    return Music(id, name, artist, songUrl, thumbnailUrl)
}

fun MediaItem.toMusic() =
    Music(
        id = null,
        name = mediaMetadata.title.toString(),
        artist = mediaMetadata.subtitle.toString(),
        songUrl = mediaId,
        thumbnailUrl = mediaMetadata.artworkUri.toString(),
    )