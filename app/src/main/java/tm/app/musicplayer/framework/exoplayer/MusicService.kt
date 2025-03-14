package tm.app.musicplayer.framework.exoplayer

import android.app.PendingIntent
import android.content.Intent
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import tm.app.musicplayer.MainActivity
import tm.app.musicplayer.other.Constants.MUSIC_SERVICE_SCOPE

class MusicService: MediaSessionService() {

    private val koinServiceScope by lazy {
        getKoin().createScope(MUSIC_SERVICE_SCOPE, named<MusicService>())
    }

    private val exoPlayer: ExoPlayer by koinServiceScope.inject()

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        val fallbackIntent = Intent(this, MainActivity::class.java)
        val activityIntent = packageManager?.getLaunchIntentForPackage(packageName)?.let {
            PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_IMMUTABLE)
        } ?: PendingIntent.getActivity(this, 0, fallbackIntent, PendingIntent.FLAG_IMMUTABLE)

        // Initialize MediaSession (MediaSessionConnector is no longer needed)
        mediaSession = MediaSession.Builder(this, exoPlayer)
            .setSessionActivity(activityIntent) // Set the app launch intent
//            .setCallback(MediaSessionCallback())
            .build()

    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? = mediaSession


    override fun onDestroy() {
        koinServiceScope.close()
        mediaSession?.run {
            exoPlayer.release()
            release()
            mediaSession = null
        }

        super.onDestroy()
    }

    private inner class MediaSessionCallback: MediaSession.Callback {
        override fun onAddMediaItems(
            mediaSession: MediaSession,
            controller: MediaSession.ControllerInfo,
            mediaItems: MutableList<MediaItem>
        ): ListenableFuture<MutableList<MediaItem>> {
            val updatedMediaItems = mediaItems.map {
                it.buildUpon().setUri(it.mediaId).build()
            }.toMutableList()

            return Futures.immediateFuture(updatedMediaItems)
        }
    }

}