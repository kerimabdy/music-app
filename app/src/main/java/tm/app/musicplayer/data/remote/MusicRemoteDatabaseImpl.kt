package tm.app.musicplayer.data.remote

import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import tm.app.musicplayer.data.mapper.toDomain
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
            postgrest.rpc("get_all_songs")
                .decodeList<MusicResponse>()
                .map { it
                    .copy(
                        url = storage.from(SupabaseBuckets.MUSICPLAYER).createSignedUrl( it.url, 100.days),
                        thumbnail = storage.from(SupabaseBuckets.MUSICPLAYER).createSignedUrl(it.thumbnail, 100.days),
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
            postgrest.from(SupabaseTables.PLAYLIST)
                .select(
                    Columns.raw("""
                            id, 
                            name, 
                            cover,
                            playlist_song_many_to_many(
                                    song(
                                    id
                                    )
                                    )
                        """.trimIndent())
                )
                .decodeList<PlaylistResponse>()
                .map { it.toDomain() }
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

    override suspend fun getMusicPlaylistById(id: Int): Flow<Resource<Playlist>> = flow {
        emit(Resource.Loading())

        runCatching {
            postgrest.from(SupabaseTables.PLAYLIST)
                .select()
                .decodeList<PlaylistResponse>()
                .get(0).toDomain()
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