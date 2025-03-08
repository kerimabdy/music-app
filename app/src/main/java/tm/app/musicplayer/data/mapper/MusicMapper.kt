package tm.app.musicplayer.data.mapper

import androidx.media3.common.MediaItem
import tm.app.musicplayer.data.remote.dto.MusicResponse
import tm.app.musicplayer.domain.model.Music

fun MusicResponse.toDomain(): Music {
    return Music(id, title, artist, url, thumbnail)
}

fun MediaItem.toMusic(): Music {
    val metadata = this.mediaMetadata
    return Music(
        id = this.mediaId.toIntOrNull() ?: -1, // Convert MediaItem ID (String) back to Int
        title = metadata.title?.toString() ?: "Unknown Title",
        artist = metadata.artist?.toString() ?: "Unknown Artist",
        url = this.localConfiguration?.uri.toString(), // Extract URI
        thumbnail = metadata.artworkUri?.toString() ?: ""
    )
}