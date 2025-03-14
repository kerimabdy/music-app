package tm.app.musicplayer.data.remote

import android.util.Log
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import tm.app.musicplayer.data.mapper.toDomain
import tm.app.musicplayer.data.remote.SupabaseFunctions.GET_ALL_PLAYLISTS
import tm.app.musicplayer.data.remote.SupabaseFunctions.GET_ALL_SONGS
import tm.app.musicplayer.data.remote.SupabaseFunctions.GET_PLAYLIST_WITH_SONGS
import tm.app.musicplayer.data.remote.dto.MusicResponse
import tm.app.musicplayer.data.remote.dto.PlaylistResponse
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.domain.model.Playlist
import tm.app.musicplayer.other.Resource
import kotlin.time.Duration.Companion.days

class MusicRemoteDatabaseImpl(
    private val postgrest: Postgrest,
    private val storage: Storage
): MusicRemoteDatabase {
    override suspend fun getAllMusics(): Flow<Resource<List<Music>>> = flow {
        emit(Resource.Loading())

        runCatching {
            postgrest.rpc(GET_ALL_SONGS)
                .decodeList<MusicResponse>()
                .map { it
                    .copy(
                        url = storage.from(SupabaseBuckets.MUSIC_PLAYER).createSignedUrl( it.url, 1.days),
                        thumbnail = storage.from(SupabaseBuckets.MUSIC_PLAYER).createSignedUrl(it.thumbnail, 1.days),
                    )
                    .toDomain()
                }
        }.onSuccess {
            emit(Resource.Success(it))
        }.onFailure {
            when (it) {
                is IOException -> emit(Resource.Error(it.message ?: "Unknown IOException")) // Network-related error
                is SerializationException -> emit(Resource.Error(it.message ?: "Unknown SerializationException"))
                else -> emit(Resource.Error(it.message ?: "Unknown error"))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllMusicPlaylists(): Flow<Resource<List<Playlist>>> = flow {
        emit(Resource.Loading())

        runCatching {
            postgrest.rpc(GET_ALL_PLAYLISTS)
                .decodeList<PlaylistResponse>()
                .map { it
                    .copy(
                        cover = storage.from(SupabaseBuckets.MUSIC_PLAYER).createSignedUrl( it.cover, 1.days),
                    )
                    .toDomain() }
        }.onSuccess {
            emit(Resource.Success(it))
        }.onFailure {
            when (it) {
                is IOException -> emit(Resource.Error(it.message ?: "Unknown IOException")) // Network-related error
                is SerializationException -> emit(Resource.Error(it.message ?: "Unknown SerializationException"))
                else -> emit(Resource.Error(it.message ?: "Unknown error"))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPlaylistWithMusics(packId: Int): Flow<Resource<Playlist>> = flow {
        emit(Resource.Loading())

        runCatching {
            postgrest.rpc(
                GET_PLAYLIST_WITH_SONGS,
                parameters = mapOf("playlist_id_param" to packId)
            )
                .decodeSingle<PlaylistResponse>().let { pack ->
                    pack.copy(
                        songs = pack.songs.map { song ->
                            song.copy(
                                thumbnail = storage.from(SupabaseBuckets.MUSIC_PLAYER).createSignedUrl( song.thumbnail, 1.days),
                                url = storage.from(SupabaseBuckets.MUSIC_PLAYER).createSignedUrl( song.url, 1.days)

                            )
                        }
                    )
                }.toDomain()
        }.onSuccess {
            emit(Resource.Success(it))
        }.onFailure {
            when (it) {
                is IOException -> emit(Resource.Error(it.message ?: "Unknown IOException")) // Network-related error
                is SerializationException -> emit(Resource.Error(it.message ?: "Unknown SerializationException"))
                else -> emit(Resource.Error(it.message ?: "Unknown error"))
            }
        }
    }.flowOn(Dispatchers.IO)
}