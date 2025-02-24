package tm.app.musicplayer.ui.home

import tm.app.musicplayer.domain.model.Music

data class HomeUiState(
    val loading: Boolean? = false,
    val musics: List<Music>? = emptyList(),
    val selectedMusic: Music? = null,
    val errorMessage: String? = null
)