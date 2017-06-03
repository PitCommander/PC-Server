package org.pitcommander.command.handler

import org.pitcommander.command.Command
import org.pitcommander.command.Replies
import org.pitcommander.command.Reply

/*
 * PCServer - Created on 5/28/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 5/28/17
 */
abstract class Handler {
    abstract fun handle(command: Command): Reply

    var reply = Replies.NONE
    val payload = HashMap<String, Any>()
}