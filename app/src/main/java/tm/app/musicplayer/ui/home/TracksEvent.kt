package tm.app.musicplayer.ui.home

import tm.app.musicplayer.domain.model.Music

sealed interface TracksEvent {
    object FetchMusic: TracksEvent
    data class OnMusicSelected(val musicIdx: Int): TracksEvent
}