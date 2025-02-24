package tm.app.musicplayer.di

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import org.koin.dsl.module
import tm.app.musicplayer.data.service.MusicService

val serviceModule = module {
    scope<MusicService> {
        scoped { AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
            .setUsage(C.USAGE_MEDIA)
            .build()
        }

        scoped {
            val context: Context = get()
            val audioAttributes: AudioAttributes = get()

            ExoPlayer.Builder(context).build().apply {
                setAudioAttributes(audioAttributes, true)
                setHandleAudioBecomingNoisy(true)
            }
        }

        scoped {
            val context: Context = get()
            DefaultDataSource.Factory(context)
        }
    }
}