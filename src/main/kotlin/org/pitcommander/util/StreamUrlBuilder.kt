package org.pitcommander.util

object StreamUrlBuilder {
    fun buildUrl(type: String, file: String, channel: String): String {
        if (type.toLowerCase() != "livestream") return channel
        return "https://livestream.com/accounts/$channel/events/$file/player"
    }
}