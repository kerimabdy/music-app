package tm.app.musicplayer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import tm.app.musicplayer.data.service.MusicService
import tm.app.musicplayer.ui.MusicPlayerApp
import tm.app.musicplayer.ui.theme.MusicPlayerTheme
import tm.app.musicplayer.ui.viewmodels.SharedViewModel

class MainActivity : ComponentActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicPlayerTheme {
                sharedViewModel = koinViewModel()
                KoinAndroidContext {
                    MusicPlayerApp(
                        sharedViewModel = sharedViewModel
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        sharedViewModel.destroyMediaController()
        stopService(Intent(this, MusicService::class.java))
    }
}