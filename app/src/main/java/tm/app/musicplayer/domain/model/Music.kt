package tm.app.musicplayer.domain.model

data class Music(
    val id: Int?,
    val title: String = "",
    val artist: String = "",
    val url: String = "",
    val thumbnailUrl: String = ""
)
