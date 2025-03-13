package tm.app.musicplayer.domain.model

data class Playlist(
    val id: Int?,
    val name: String,
    val cover: String,
    val musics: List<Music>,
    val musicsCount: Int,
)