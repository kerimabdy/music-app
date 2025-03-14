package tm.app.musicplayer.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Packs

@Serializable
object Tracks

@Serializable
object Downloads

@Serializable
object Home

@Serializable
data class PackContent(val id: Int)



@Serializable
object MusicScreen

data class TopLevelRoute<T : Any>(val name: String, val route: T)