package tm.app.musicplayer.other

import tm.app.musicplayer.domain.model.Music

data class MusicControllerUiState(
    val playerState: PlayerState? = null,
    val currentMusic: Music? = null,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val isShuffleEnabled: Boolean = false,
    val isRepeatOneEnabled: Boolean = false
)