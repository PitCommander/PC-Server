package org.pitcommander.socket

import com.google.gson.Gson
import com.google.gson.JsonParseException
import org.pitcommander.command.Command
import org.pitcommander.command.CommandRouter
import org.pitcommander.command.Replies
import org.pitcommander.command.Reply
import org.slf4j.LoggerFactory
import org.zeromq.ZMQ

/*
 * PCServer - Created on 5/24/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 5/24/17
 */
object CommandSock : Runnable {
    private val logger = LoggerFactory.getLogger(javaClass)

    private var port = 0
    private val gson = Gson()

    fun setup(port: Int) {
        this.port = port
    }

    override fun run() {
        val context = ZMQ.context(1)
        val socket = context.socket(ZMQ.REP)
        socket.receiveTimeOut = 30
        socket.bind("tcp://*:$port")

        var command: String?
        var reply: Reply
        var replyEncoded: String
        while (!Thread.interrupted()) {
            command = socket.recvStr()
            if (command != null) {
                logger.debug("Got command: $command")
                try {
                    reply = CommandRouter.route(gson.fromJson(command, Command::class.java))
                } catch (e: JsonParseException) {
                    reply = Reply(Replies.GENERAL_FAIL, HashMap<String, Any?>())
                }
                replyEncoded = gson.toJson(reply)
                socket.send(replyEncoded)
                logger.debug("Replying: $replyEncoded")
            }
            try {
                Thread.sleep(10L)
            } catch(e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }
}