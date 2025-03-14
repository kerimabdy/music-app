package tm.app.musicplayer.ui.packcontent

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tm.app.musicplayer.domain.useCase.AddMediaItemsUseCase
import tm.app.musicplayer.domain.useCase.GetPlaylistWithMusics
import tm.app.musicplayer.domain.useCase.PlayMusicUseCase
import tm.app.musicplayer.other.Resource
import tm.app.musicplayer.ui.navigation.PackContent

class PackContentViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPlaylistWithMusics: GetPlaylistWithMusics,
    private val addMediaItemsUseCase: AddMediaItemsUseCase,
    private val playMusicUseCase: PlayMusicUseCase
): ViewModel() {
    private val pack = savedStateHandle.toRoute<PackContent>()
    var packContentUiState = MutableStateFlow(PackContentUiState())
        private set

    fun onEvent(event: PackContentEvent) {
        when (event) {
            PackContentEvent.FetchData -> getPlaylist()
            is PackContentEvent.OnMusicSelected -> onMusicSelected(event.musicIdx)
        }
    }

    private fun getPlaylist() {
        val packId = pack.id
        viewModelScope.launch {
            getPlaylistWithMusics.invoke(packId).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        Log.d("Error", result.message ?: "")
                        packContentUiState.update {
                            it.copy(loading = false, errorMessage = result.message, data = null)
                        }
                    }
                    is Resource.Loading -> {
                        packContentUiState.update {
                            it.copy(loading = true, errorMessage = null)
                        }
                    }
                    is Resource.Success -> {
                        Log.d("Result", result.data.toString())

                        packContentUiState.update {
                            it.copy(loading = false, errorMessage = null, data = result.data)
                        }
                    }
                }
            }
        }
    }

    private fun onMusicSelected(musicId: Int) {
        packContentUiState.value.data?.let { data ->
            addMediaItemsUseCase(data.musics)
            playMusicUseCase(musicId)
        }
    }
}