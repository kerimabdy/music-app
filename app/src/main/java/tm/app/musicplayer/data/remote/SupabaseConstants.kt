package tm.app.musicplayer.data.remote

object SupabaseTables {
    const val SONGS = "song"
    const val PLAYLIST = "playlist"
}

object SupabaseBuckets {
    const val MUSICPLAYER = "Music%20Player"
}

object SupabaseFunctions {
    const val GETALLSONGS = "SELECT * from get_all_songs();"
}