package tm.app.musicplayer.ui.packs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import tm.app.musicplayer.domain.useCase.GetAllMusicPlaylistsUseCase
import tm.app.musicplayer.other.Resource

class PacksViewModel(
    val getAllMusicPlaylistsUseCase: GetAllMusicPlaylistsUseCase
): ViewModel() {
    var packsUiState = MutableStateFlow<PacksUiState>(PacksUiState.Loading)
        private set

    init {
        onEvent(PacksEvent.FetchPlaylist)
    }

    fun onEvent(event: PacksEvent) {
        when (event) {
            is PacksEvent.FetchPlaylist -> getAllMusicPlaylists()
        }
    }

    private fun getAllMusicPlaylists() {
        viewModelScope.launch {
            getAllMusicPlaylistsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        packsUiState.value = PacksUiState.Loading
                    }
                    is Resource.Success -> {
                        result.data?.let {
                            packsUiState.value = PacksUiState.Success(
                                data = it
                            )
                        }
                    }
                    is Resource.Error -> {
                        packsUiState.value = PacksUiState.Error(
                                title = "Network Error",
                                subtitle = "",
                                action = { onEvent(PacksEvent.FetchPlaylist) }
                        )
                    }
                }
            }
        }
    }


}