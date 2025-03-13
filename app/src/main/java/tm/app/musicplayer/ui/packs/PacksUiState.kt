package tm.app.musicplayer.ui.packs

import tm.app.musicplayer.domain.model.Playlist
import tm.app.musicplayer.ui.musicscreen.MusicEvent

sealed class PacksUiState {
    object Loading: PacksUiState()

    data class Success(
        val data: List<Playlist>
    ): PacksUiState()

    data class Error(
        val title: String,
        val subtitle: String,
        val action: () -> Unit
    ): PacksUiState()
}