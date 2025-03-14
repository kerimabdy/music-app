package tm.app.musicplayer.ui.packcontent

import tm.app.musicplayer.domain.model.Playlist

data class PackContentUiState(
    val loading: Boolean = false,
    val data: Playlist? = null,
    val errorMessage: String? = null
)
