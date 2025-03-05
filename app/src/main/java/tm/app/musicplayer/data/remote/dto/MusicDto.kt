package tm.app.musicplayer.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusicResponse(
    val id: Int = 0,
    @SerialName("created_at")
    val createdAt: String = "",
    val name: String?,
    @SerialName("singer")
    val artist: String = "",
    val songUrl: String = "",
    val thumbnailUrl: String = ""
)
