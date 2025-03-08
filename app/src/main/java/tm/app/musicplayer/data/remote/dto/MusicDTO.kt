package tm.app.musicplayer.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusicResponse(
    val id: Int = 0,
    @SerialName("created_at")
    val createdAt: String = "",
    val title: String = "",
    val artist: String = "",
    val url: String = "",
    val thumbnail: String = ""
)
