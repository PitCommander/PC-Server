package org.pitcommander.socket

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.pitcommander.announcement.Announcement
import org.slf4j.LoggerFactory
import org.zeromq.ZMQ
import java.lang.reflect.Modifier
import java.util.*

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

object AnnounceSock : Runnable {
    private val logger = LoggerFactory.getLogger(javaClass)

    private class RoutableAnnouncement(announcement: Announcement) {
        val id = announcement.getName()
        val payload = announcement
    }

    private var port = 0
    private val gson = GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create()
    private val announcementQueue = Vector<RoutableAnnouncement>()

    fun setup(port: Int) {
        this.port = port
    }

    fun announce(announcement: Announcement) {
        announcementQueue.add(RoutableAnnouncement(announcement))
    }

    override fun run() {
        val context = ZMQ.context(1)
        val socket = context.socket(ZMQ.PUB)
        socket.bind("tcp://*:$port")

        while (!Thread.interrupted()) {
            while (announcementQueue.size > 0) {
                val current = announcementQueue.removeAt(0)
                val toSend = gson.toJson(current)
                socket.send(toSend)
                logger.debug("Announce: $toSend")
            }
            try {
                Thread.sleep(20)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }
}