package tm.app.musicplayer.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.useCase.AddMediaItemsUseCase
import tm.app.musicplayer.domain.useCase.GetAllMusicsUseCase
import tm.app.musicplayer.domain.useCase.PauseMusicUseCase
import tm.app.musicplayer.domain.useCase.PlayMusicUseCase
import tm.app.musicplayer.domain.useCase.ResumeMusicUseCase
import tm.app.musicplayer.domain.useCase.SkipToNextMusicUseCase
import tm.app.musicplayer.domain.useCase.SkipToPreviousMusicUseCase
import tm.app.musicplayer.other.Resource

class HomeViewModel(
    private val getAllMusicUseCase: GetAllMusicsUseCase,
    private val addMediaItemsUseCase: AddMediaItemsUseCase,
    private val playMusicUseCase: PlayMusicUseCase,
    private val pauseMusicUseCase: PauseMusicUseCase,
    private val resumeMusicUseCase: ResumeMusicUseCase,
    private val skipToNextMusicUseCase: SkipToNextMusicUseCase,
    private val skipToPreviousMusicUseCase: SkipToPreviousMusicUseCase,
): ViewModel() {
    var homeUiState = MutableStateFlow(HomeUiState())
        private set

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.FetchMusic -> getMusic()
            is HomeEvent.PlayMusic -> playMusic(event.musicId)
            is HomeEvent.OnMusicSelected -> onMusicSelected(event.selectedMusic)
            HomeEvent.PauseMusic -> pauseMusic()
            HomeEvent.ResumeMusic -> resumeMusic()
            HomeEvent.SkipToNextMusic -> skipToNextMusic()
            HomeEvent.SkipToPreviousMusic -> skipToPreviousMusic()
        }
    }

    private fun getMusic() {

        viewModelScope.launch {
            getAllMusicUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { musics ->
                            Log.d("SongInViewModel", musics.toString())
                            addMediaItemsUseCase(musics)
                        }
                        homeUiState.update { it.copy(
                            loading = false,
                            errorMessage = null,
                            musics = result.data
                        ) }
                    }
                    is Resource.Loading -> {
                        homeUiState.update { it.copy(
                            loading = true,
                            errorMessage = null
                        )
                        }
                    }
                    is Resource.Error -> {
                        homeUiState.update { it.copy(
                            loading = false,
                            errorMessage = result.message
                        )
                        }
                    }
                }
            }


        }
    }

    private fun playMusic(musicId: Int) {
//        homeUiState. {
//            musics?.indexOf(selectedMusic)?.let { music ->
//            }
//        }
        playMusicUseCase(musicId)

    }

    private fun onMusicSelected(selectedMusic: Music) {
        homeUiState.update { it.copy(selectedMusic = selectedMusic)}
    }

    private fun skipToPreviousMusic() {
        skipToPreviousMusicUseCase { selectedMusic ->
            homeUiState.update { it.copy(selectedMusic = selectedMusic)}
        }
    }

    private fun skipToNextMusic() {
        skipToNextMusicUseCase { selectedMusic ->
            homeUiState.update { it.copy(selectedMusic = selectedMusic)}
        }
    }

    private fun resumeMusic() {
        resumeMusicUseCase()
    }

    private fun pauseMusic() {
        pauseMusicUseCase()
    }

}