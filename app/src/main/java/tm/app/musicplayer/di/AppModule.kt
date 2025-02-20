package tm.app.musicplayer.di

import ProvideSupabaseClient
import ProvideSupabaseDatabase
import ProvideSupabaseStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tm.app.musicplayer.data.remote.MusicService
import tm.app.musicplayer.data.remote.MusicServiceImpl
import tm.app.musicplayer.data.repository.MusicRepositoryImpl
import tm.app.musicplayer.domain.music.repository.MusicRepository

val appModule = module {
    single { ProvideSupabaseClient() }
    single { ProvideSupabaseDatabase(get())}
    single { ProvideSupabaseStorage(get()) }

    singleOf(::MusicServiceImpl) bind MusicService::class

    single<MusicRepository> { MusicRepositoryImpl(get()) }
}