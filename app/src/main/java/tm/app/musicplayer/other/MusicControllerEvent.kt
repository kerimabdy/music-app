package tm.app.musicplayer.other

sealed interface MusicControllerEvent {
    data class UpdatePosition(val newPosition: Long): MusicControllerEvent
    data class PlayPauseToggle(val isPlaying: Boolean): MusicControllerEvent
}