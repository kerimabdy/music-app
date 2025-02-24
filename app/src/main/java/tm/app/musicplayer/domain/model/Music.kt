package tm.app.musicplayer.domain.model

data class Music(
    val id: Int?,
    val name: String = "",
    val artist: String = "",
    val songUrl: String = "",
    val thumbnailUrl: String = ""
)
