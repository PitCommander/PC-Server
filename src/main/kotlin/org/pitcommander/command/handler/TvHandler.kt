package org.pitcommander.command.handler

import org.pitcommander.announcement.TvAnnouncement
import org.pitcommander.command.Command
import org.pitcommander.command.Commands
import org.pitcommander.command.Replies
import org.pitcommander.command.Reply
import org.pitcommander.socket.AnnounceSock

/*
 * PCServer - Created on 6/2/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 6/2/17
 */
object TvHandler : Handler() {
    override fun handleImpl(command: Command) {
        when (command.id) {
            Commands.TV_SET -> {
                val toSend = TvAnnouncement()
                toSend.putAll(command.payload)
                AnnounceSock.announce(toSend)
                reply = Replies.GENERAL_ACK
                payload.put("message", "TV packet reflected you goon lel top kek")
            }
        }
    }
}