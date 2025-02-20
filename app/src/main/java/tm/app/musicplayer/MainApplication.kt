package tm.app.musicplayer

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import tm.app.musicplayer.di.appModule

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}