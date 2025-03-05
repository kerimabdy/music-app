package tm.app.musicplayer.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistResponse(
    val id: Int,
    val name: String,
    val cover: String,
    @SerialName("playlist_song_many_to_many")
    val playlistSongs: List<PlaylistSongManyToMany>
) {
    val songCount: Int = playlistSongs.size
}


@Serializable
data class PlaylistSongManyToMany(
    val song: MusicResponse
)