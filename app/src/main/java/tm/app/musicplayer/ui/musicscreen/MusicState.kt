package tm.app.musicplayer.ui.musicscreen

sealed class MusicEvent {
    data object PauseMusic : MusicEvent()
    data object ResumeMusic : MusicEvent()
    data object SkipToNextMusic : MusicEvent()
    data object SkipToPreviousMusic : MusicEvent()
    data class SeekMusicToPosition(val position: Long) : MusicEvent()
}