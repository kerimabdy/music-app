package tm.app.musicplayer.domain.service

import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.other.PlayerState

interface MusicController {
    var mediaControllerCallback: (
            (
            playerState: PlayerState,
            currentMusic: Music?,
            currentPosition: Long,
            totalDuration: Long,
            isShuffleEnabled: Boolean,
            isRepeatOnEnabled: Boolean
                    ) -> Unit
            )?

    fun addMediaItems(musics: List<Music>)

    fun play(mediaItemIndex: Int)

    fun resume()

    fun pause()

    fun getCurrentPosition(): Long

    fun destroy()

    fun skipToNextMusic()

    fun skipToPreviousMusic()

    fun getCurrentMusic(): Music?

    fun seekTo(position: Long)
}