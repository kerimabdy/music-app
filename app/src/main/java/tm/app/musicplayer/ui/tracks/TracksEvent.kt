package tm.app.musicplayer.ui.tracks

sealed interface TracksEvent {
    object FetchMusic: TracksEvent
    data class OnMusicSelected(val musicIdx: Int): TracksEvent
}