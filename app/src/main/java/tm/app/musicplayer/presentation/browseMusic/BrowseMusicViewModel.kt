package tm.app.musicplayer.presentation.browseMusic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tm.app.musicplayer.domain.music.model.Music
import tm.app.musicplayer.domain.music.useCases.GetAllMusicsUseCase

class BrowseMusicViewModel(
    private val getPostUseCase: GetAllMusicsUseCase
): ViewModel() {
    private val _state = MutableStateFlow(MusicState())
    val state = _state.asStateFlow()

    init {
        onEvent(MusicEvent.GetMusic)
    }


    fun onEvent(event: MusicEvent) {
        when (event) {
            is MusicEvent.GetMusic -> getMusic()
            is MusicEvent.TogglePlayPause -> togglePlayPause()
            is MusicEvent.SkipToMusic -> skipToMusic(event.music)
        }
    }


    private fun getMusic() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            try {
                val musicList = getPostUseCase.invoke()
                _state.update { it.copy(data = musicList, loading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message ?: "Unknown error", loading = false )}
            }
        }
    }

    private fun togglePlayPause() {
//        TODO: implement toggle play pause
    }

    private fun skipToMusic(music: Music) {
//        TODO: implement skip to music
    }

}