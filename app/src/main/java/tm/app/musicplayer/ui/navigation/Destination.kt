package tm.app.musicplayer.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Packs

@Serializable
object Tracks

@Serializable
object Downloads

@Serializable
object Main

@Serializable
object Home

@Serializable
object MusicScreen

data class TopLevelRoute<T : Any>(val name: String, val route: T)