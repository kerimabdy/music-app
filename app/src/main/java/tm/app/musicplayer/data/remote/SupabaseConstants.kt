package tm.app.musicplayer.data.remote

object SupabaseTables {
    const val SONGS = "song"
    const val PLAYLIST = "playlist"
}

object SupabaseBuckets {
    const val MUSIC_PLAYER = "Music%20Player"
}

object SupabaseFunctions {
    const val GET_ALL_SONGS = "get_all_songs"
    const val GET_ALL_PLAYLISTS = "get_all_playlists"
    const val GET_PLAYLIST_WITH_SONGS = "get_playlist_with_songs"
}