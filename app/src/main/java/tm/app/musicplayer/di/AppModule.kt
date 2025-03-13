package tm.app.musicplayer.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import tm.app.musicplayer.data.repository.MusicRepositoryImpl
import tm.app.musicplayer.domain.repository.MusicRepository
import tm.app.musicplayer.domain.useCase.AddMediaItemsUseCase
import tm.app.musicplayer.domain.useCase.DestroyMediaControllerUseCase
import tm.app.musicplayer.domain.useCase.GetAllMusicPlaylistsUseCase
import tm.app.musicplayer.domain.useCase.GetAllMusicsUseCase
import tm.app.musicplayer.domain.useCase.GetCurrentMusicPositionUseCase
import tm.app.musicplayer.domain.useCase.GetMusicPlaylistByIdUseCase
import tm.app.musicplayer.domain.useCase.PauseMusicUseCase
import tm.app.musicplayer.domain.useCase.PlayMusicUseCase
import tm.app.musicplayer.domain.useCase.ResumeMusicUseCase
import tm.app.musicplayer.domain.useCase.SeekMusicToPositionUseCase
import tm.app.musicplayer.domain.useCase.SetMediaControllerCallbackUseCase
import tm.app.musicplayer.domain.useCase.SkipToNextMusicUseCase
import tm.app.musicplayer.domain.useCase.SkipToPreviousMusicUseCase
import tm.app.musicplayer.ui.home.TracksViewModel
import tm.app.musicplayer.ui.musicscreen.MusicViewModel
import tm.app.musicplayer.ui.packs.PacksViewModel
import tm.app.musicplayer.ui.viewmodels.SharedViewModel

val appModule = module {
    single<MusicRepository> { MusicRepositoryImpl(get()) }

    single { GetAllMusicsUseCase(get()) }
    single { GetAllMusicPlaylistsUseCase(get()) }
    single { GetMusicPlaylistByIdUseCase(get()) }

    single { AddMediaItemsUseCase(get()) }
    single { DestroyMediaControllerUseCase(get()) }
    single { GetCurrentMusicPositionUseCase(get()) }
    single { PauseMusicUseCase(get()) }
    single { PlayMusicUseCase(get()) }
    single { ResumeMusicUseCase(get()) }
    single { SeekMusicToPositionUseCase(get()) }
    single { SetMediaControllerCallbackUseCase(get()) }
    single { SkipToNextMusicUseCase(get()) }
    single { SkipToPreviousMusicUseCase(get())}

    viewModelOf(::PacksViewModel)
    viewModelOf(::TracksViewModel)
    viewModelOf(::MusicViewModel)
    viewModelOf(::SharedViewModel)
}