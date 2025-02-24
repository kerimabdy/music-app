package tm.app.musicplayer.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tm.app.musicplayer.data.repository.MusicRepositoryImpl
import tm.app.musicplayer.data.service.MusicControllerImpl
import tm.app.musicplayer.domain.repository.MusicRepository
import tm.app.musicplayer.domain.service.MusicController
import tm.app.musicplayer.domain.useCase.AddMediaItemsUseCase
import tm.app.musicplayer.domain.useCase.DestroyMediaControllerUseCase
import tm.app.musicplayer.domain.useCase.GetAllMusicsUseCase
import tm.app.musicplayer.domain.useCase.GetCurrentMusicPositionUseCase
import tm.app.musicplayer.domain.useCase.PauseMusicUseCase
import tm.app.musicplayer.domain.useCase.PlayMusicUseCase
import tm.app.musicplayer.domain.useCase.ResumeMusicUseCase
import tm.app.musicplayer.domain.useCase.SeekMusicToPositionUseCase
import tm.app.musicplayer.domain.useCase.SetMediaControllerCallbackUseCase
import tm.app.musicplayer.domain.useCase.SkipToNextMusicUseCase
import tm.app.musicplayer.domain.useCase.SkipToPreviousMusicUseCase
import tm.app.musicplayer.ui.home.HomeViewModel
import tm.app.musicplayer.ui.viewmodels.SharedViewModel

val appModule = module {

    single<MusicRepository> { MusicRepositoryImpl(get()) }
    singleOf(::MusicControllerImpl)  bind MusicController::class

    single { GetAllMusicsUseCase(get()) }
    single { AddMediaItemsUseCase(get()) }
    single { DestroyMediaControllerUseCase(get()) }
    single { GetCurrentMusicPositionUseCase(get()) }
    single { PauseMusicUseCase(get()) }
    single { PlayMusicUseCase(get()) }
    single { ResumeMusicUseCase(get()) }
    single { SeekMusicToPositionUseCase(get()) }
    single { SetMediaControllerCallbackUseCase(get()) }
    single { SkipToNextMusicUseCase(get()) }
    single { SkipToPreviousMusicUseCase(get()) }

    viewModelOf(::HomeViewModel)
    viewModelOf(::SharedViewModel)
}