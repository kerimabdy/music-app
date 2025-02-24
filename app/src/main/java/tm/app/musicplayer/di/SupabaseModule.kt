package tm.app.musicplayer.di

import ProvideSupabaseClient
import ProvideSupabaseDatabase
import ProvideSupabaseStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tm.app.musicplayer.data.remote.MusicRemoteDatabase
import tm.app.musicplayer.data.remote.MusicRemoteDatabaseImpl

val supabaseModule = module {
    single { ProvideSupabaseClient() }
    single { ProvideSupabaseDatabase(get())}
    single { ProvideSupabaseStorage(get()) }

    singleOf(::MusicRemoteDatabaseImpl) bind MusicRemoteDatabase::class
}