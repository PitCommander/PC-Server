package org.pitcommander.runtime

import org.pitcommander.announcement.TimeTickAnnouncement
import org.pitcommander.socket.AnnounceSock
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.concurrent.atomic.AtomicLong

/*
 * PCServer - Created on 5/26/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 5/26/17
 */
object TimeTicker : Runnable {
    private val logger = LoggerFactory.getLogger(javaClass)

    private var interval = 1L
    private val currentTime = AtomicLong(0L)
    private var debugMode = false

    fun setup(interval: Long, debugMode: Boolean = false, startingTime: Long = 0L) {
        this.interval = interval
        this.debugMode = debugMode
        currentTime.set(startingTime)
    }

    fun getCurrentTime() = currentTime.get()

    override fun run() {
        while (!Thread.interrupted()) {
            if (debugMode) currentTime.addAndGet(100L)
            else currentTime.set(Instant.now().toEpochMilli() / 1000L)

            AnnounceSock.announce(TimeTickAnnouncement(currentTime.get()))
            logger.debug("TIME: ${currentTime.get()}")

            try {
                Thread.sleep(interval)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }
}