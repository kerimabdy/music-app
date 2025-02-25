package tm.app.musicplayer.data.remote

import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import tm.app.musicplayer.data.mapper.toDomain
import tm.app.musicplayer.data.remote.dto.MusicResponse
import tm.app.musicplayer.domain.model.Music
import tm.app.musicplayer.other.Resource

class MusicRemoteDatabaseImpl(
    private val postgrest: Postgrest
): MusicRemoteDatabase {
    override suspend fun getAllMusics(): Resource<List<Music>> = withContext(Dispatchers.IO) {
        try {
            val result = postgrest.from(SupabaseTables.SONGS)
                .select()
                .decodeList<MusicResponse>()
                .map { it.toDomain() }
            Resource.Success(result)
        } catch (e: IOException) {
            Resource.Error(e.message ?: "Unknown error") // Network-related error
        } catch (e: SerializationException) {
            Resource.Error(e.message ?: "Unknown error")  // JSON parsing error
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")  // Catch-all for other errors
        }
    }
}