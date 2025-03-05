package tm.app.musicplayer.data.service

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import tm.app.musicplayer.data.mapper.toMusic
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.service.MusicController
import tm.app.musicplayer.other.PlayerState

class MusicControllerImpl(context: Context) : MusicController {

    private var mediaControllerFuture: ListenableFuture<MediaController>
    private val mediaController: MediaController?
        get() = if (mediaControllerFuture.isDone) mediaControllerFuture.get() else null

    override var mediaControllerCallback: (
        (
        playerState: PlayerState,
        currentMusic: Music?,
        currentPosition: Long,
        totalDuration: Long,
        isShuffleEnabled: Boolean,
        isRepeatOnEnabled: Boolean
    ) -> Unit
    )? = null

    init {
        val sessionToken = SessionToken(context, ComponentName(context, MusicService::class.java))
        mediaControllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        mediaControllerFuture.addListener({ controllerListener() }, MoreExecutors.directExecutor())

    }

    private fun controllerListener() {
        mediaController?.addListener(object: Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)

                with(player) {
                    mediaControllerCallback?.invoke(
                        playbackState.toPlayerState(isPlaying),
                        currentMediaItem?.toMusic(),
                        currentPosition.coerceAtLeast(0L),
                        duration.coerceAtLeast(0L),
                        shuffleModeEnabled,
                        repeatMode == Player.REPEAT_MODE_ONE
                    )
                }
            }
        })
    }

    private fun Int.toPlayerState(isPlaying: Boolean) =
        when (this) {
            Player.STATE_IDLE -> PlayerState.STOPPED
            Player.STATE_ENDED -> PlayerState.STOPPED
            else -> if (isPlaying) PlayerState.PLAYING else PlayerState.PAUSED
        }

    override fun addMediaItems(musics: List<Music>) {
        val mediaItems = musics.map {
            MediaItem.Builder()
                .setMediaId(it.id.toString())
                .setUri(it.url)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(it.title)
                        .setSubtitle(it.artist)
                        .setArtist(it.artist)
                        .setArtworkUri(Uri.parse(it.thumbnailUrl))
                        .build()
                )
                .build()
        }
    }

    fun addPlaylist(musics: List<Music>, playlistId: String) {
        val mediaItems = musics.map {
            MediaItem.Builder()
                .setMediaId(it.id.toString())
                .setUri(it.url)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(it.title)
                        .setSubtitle(it.artist)
                        .setArtist(it.artist)
                        .setArtworkUri(Uri.parse(it.thumbnailUrl))
                        .setExtras( Bundle().apply {
                            putString("playlistID", playlistId)
                        })
                        .build()
                )
                .build()
        }
    }

    override fun play(mediaItemIndex: Int) {
        mediaController?.apply {
            seekToDefaultPosition(mediaItemIndex)
            playWhenReady = true
            prepare()
        }
    }

    override fun resume() {
        mediaController?.play()
    }

    override fun pause() {
        mediaController?.pause()
    }

    override fun getCurrentPosition(): Long = mediaController?.currentPosition ?: 0

    override fun destroy() {
        MediaController.releaseFuture(mediaControllerFuture)
        mediaControllerCallback = null
    }


    override fun skipToNextMusic() {
        mediaController?.seekToNext()
    }

    override fun skipToPreviousMusic() {
        mediaController?.seekToPrevious()
    }

    override fun getCurrentMusic(): Music? = mediaController?.currentMediaItem?.toMusic()

    override fun seekTo(position: Long) {
        mediaController?.seekTo(position)
    }
}