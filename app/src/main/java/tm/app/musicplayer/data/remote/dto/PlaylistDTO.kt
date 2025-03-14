package tm.app.musicplayer.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistResponse(
    val id: Int,
    val name: String,
    val cover: String,
    @SerialName("song_count")
    val count: Int,
    val songs: List<MusicResponse> = emptyList()
)
