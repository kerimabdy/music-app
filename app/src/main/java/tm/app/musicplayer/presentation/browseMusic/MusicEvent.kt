package tm.app.musicplayer.presentation.browseMusic

import tm.app.musicplayer.domain.music.model.Music

sealed interface MusicEvent {
    data object GetMusic: MusicEvent

    data object TogglePlayPause: MusicEvent
    data class SkipToMusic(
        val music: Music
    ): MusicEvent
}