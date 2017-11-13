package org.pitcommander.command.handler

import org.pitcommander.command.Command
import org.pitcommander.command.Replies

object PingHandler: Handler() {
    override fun handleImpl(command: Command) {
        reply = Replies.PONG
    }
}