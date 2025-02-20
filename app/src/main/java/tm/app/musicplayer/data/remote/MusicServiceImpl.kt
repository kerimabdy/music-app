package tm.app.musicplayer.data.remote

import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tm.app.musicplayer.data.remote.dto.MusicResponse

class MusicServiceImpl(
    private val postgrest: Postgrest
): MusicService {
    override suspend fun getMusic(): List<MusicResponse> {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from(SupabaseTables.SONGS).select().decodeList<MusicResponse>()
            result
        }
    }
}