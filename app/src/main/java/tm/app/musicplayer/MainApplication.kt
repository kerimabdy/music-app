package tm.app.musicplayer

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import tm.app.musicplayer.di.appModule
import tm.app.musicplayer.di.serviceModule
import tm.app.musicplayer.di.supabaseModule

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                appModule,
                serviceModule,
                supabaseModule
            )
        }
    }
}