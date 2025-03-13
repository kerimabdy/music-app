package tm.app.musicplayer.data.mapper

import tm.app.musicplayer.data.remote.dto.PlaylistResponse
import tm.app.musicplayer.domain.model.Playlist

fun PlaylistResponse.toDomain(): Playlist {
    return Playlist(
        id = id,
        name = name,
        cover = cover,
        musicsCount = count,
        musics = emptyList()
    )
}