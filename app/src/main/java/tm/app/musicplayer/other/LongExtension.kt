package tm.app.musicplayer.other

import java.util.Locale

fun Long.toTime(): String {
    val stringBuffer = StringBuffer()

    val minutes = (this / 60000).toInt()
    val seconds = (this % 60000 / 1000).toInt()

    stringBuffer
        .append(String.format(Locale.US, "%02d", minutes))
        .append(":")
        .append(String.format(Locale.US, "%02d", seconds))

    return stringBuffer.toString()
}