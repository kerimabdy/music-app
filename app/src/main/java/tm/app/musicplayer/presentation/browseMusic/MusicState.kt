package tm.app.musicplayer.presentation.browseMusic

import tm.app.musicplayer.domain.music.model.Music

data class MusicState(
    val data: List<Music> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)
