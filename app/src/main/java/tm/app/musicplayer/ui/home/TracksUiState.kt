package tm.app.musicplayer.ui.home

import tm.app.musicplayer.domain.model.Music

data class TracksUiState(
    val loading: Boolean = false,
    val musics: List<Music> = emptyList(),
    val errorMessage: String? = null
)