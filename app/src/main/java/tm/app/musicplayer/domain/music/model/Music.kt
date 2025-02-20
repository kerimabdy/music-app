package tm.app.musicplayer.domain.music.model

data class Music(
    val id: Int,
    val createdAt: String ,
    val name: String = "",
    val artist: String = "",
    val songUrl: String = "",
    val thumbnailUrl: String = ""
)
