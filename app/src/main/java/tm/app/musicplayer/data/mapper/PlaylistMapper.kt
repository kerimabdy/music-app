package tm.app.musicplayer.data.mapper

import tm.app.musicplayer.data.remote.dto.PlaylistResponse
import tm.app.musicplayer.data.remote.dto.PlaylistSongManyToMany
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.model.Playlist

fun PlaylistResponse.toDomain(): Playlist {
    return Playlist(
        id = id,
        name = name,
        isCurated = true,
        musics = playlistSongs.map { it.toDomain() },
        musicsCount = songCount
    )
}

fun PlaylistSongManyToMany.toDomain(): Music {
    return Music(
        id = song.id,
        title = song.name,
        artist = song.artist,
        thumbnailUrl = song.thumbnailUrl,
        url = song.songUrl
    )
}