package tm.app.musicplayer.ui.home

import tm.app.musicplayer.domain.model.Music

sealed class HomeEvent {
    object PlayMusic : HomeEvent()
    object PauseMusic : HomeEvent()
    object ResumeMusic : HomeEvent()
    object FetchMusic : HomeEvent()
    object SkipToNextMusic : HomeEvent()
    object SkipToPreviousMusic : HomeEvent()
    data class OnMusicSelected(val selectedMusic: Music) : HomeEvent()
}