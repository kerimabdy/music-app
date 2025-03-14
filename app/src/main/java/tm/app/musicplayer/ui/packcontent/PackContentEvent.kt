package tm.app.musicplayer.ui.home.component.tm.app.musicplayer.ui.packcontent


sealed interface PackContentEvent {
    object FetchData: PackContentEvent
    data class OnMusicSelected(val musicIdx: Int): PackContentEvent
}