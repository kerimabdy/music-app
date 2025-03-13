package tm.app.musicplayer.ui.packs

sealed interface PacksEvent {
    object FetchPlaylist: PacksEvent
}