package tm.app.musicplayer.domain.model

data class Playlist(
    val id: Int?,
    val name: String,
    val musics: List<Music>,
    val musicsCount: Int,
    val isCurated: Boolean = false // Distinguishes server-curated vs. custom
)