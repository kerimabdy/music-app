package tm.app.musicplayer.data.mapper

import tm.app.musicplayer.data.remote.dto.PlaylistResponse
import tm.app.musicplayer.data.remote.dto.PlaylistMusicManyToMany
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.model.Playlist

fun PlaylistResponse.toDomain(): Playlist {
    return Playlist(
        id = id,
        name = name,
        isCurated = true,
        musics = playlistMusics.map { it.toDomain() },
        musicsCount = musicCount
    )
}

fun PlaylistMusicManyToMany.toDomain(): Music {
    return Music(
        id = music.id,
        title = music.title,
        artist = music.artist,
        thumbnail = music.thumbnail,
        url = music.url
    )
}