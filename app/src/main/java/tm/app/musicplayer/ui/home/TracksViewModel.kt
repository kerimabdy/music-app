package tm.app.musicplayer.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.useCase.AddMediaItemsUseCase
import tm.app.musicplayer.domain.useCase.GetAllMusicsUseCase
import tm.app.musicplayer.domain.useCase.PlayMusicUseCase
import tm.app.musicplayer.other.Resource

class TracksViewModel(
    private val getAllMusicUseCase: GetAllMusicsUseCase,
    private val playMusicUseCase: PlayMusicUseCase,
    private val addMediaItemsUseCase: AddMediaItemsUseCase
) : ViewModel() {
    var tracksUiState = MutableStateFlow(TracksUiState())
        private set

    init {
        onEvent(TracksEvent.FetchMusic)
    }

    fun onEvent(event: TracksEvent) {
        when (event) {
            TracksEvent.FetchMusic -> getMusic()
            is TracksEvent.OnMusicSelected -> onMusicSelected(event.musicIdx)
        }
    }

    private fun getMusic() {
        viewModelScope.launch {
            getAllMusicUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { musics ->
                            tracksUiState.update { it.copy(musics = musics, loading = false) }
                        }
                    }

                    is Resource.Loading -> {
                        tracksUiState.update { it.copy(errorMessage = null, loading = true)}
                    }

                    is Resource.Error -> {
                        result.message?.let { message ->
                            {
                                tracksUiState.update { it.copy(errorMessage = message, loading = false)}
                            }

                        }
                    }
                }
            }


        }
    }

    private fun playMusic(musicId: Int) {
      addMediaItemsUseCase(tracksUiState.value.musics)
        playMusicUseCase(musicId)

    }

    private fun onMusicSelected(musicId: Int) {
        addMediaItemsUseCase(tracksUiState.value.musics)
        playMusicUseCase(musicId)
    }

}