package tm.app.musicplayer.ui.home

import tm.app.musicplayer.domain.model.Music

sealed class HomeEvent {
    object FetchMusic : HomeEvent()
    object SkipToNextMusic : HomeEvent()
    object SkipToPreviousMusic : HomeEvent()
    data class OnMusicSelected(val selectedMusic: Music) : HomeEvent()
    data class PlayMusic(val musicId: Int) : HomeEvent()
    object PauseMusic : HomeEvent()
    object ResumeMusic : HomeEvent()
}