package org.pitcommander

import ch.qos.logback.classic.Level
import org.pitcommander.config.ActiveConfig
import org.pitcommander.container.MatchContainer
import org.pitcommander.container.checklist.ChecklistContainerBase
import org.pitcommander.container.checklist.MatchChecklistContainer
import org.pitcommander.container.checklist.SafetyChecklistContainer
import org.pitcommander.runtime.TbaPoller
import org.pitcommander.runtime.TimeTicker
import org.pitcommander.socket.AnnounceSock
import org.pitcommander.socket.CommandSock
import org.slf4j.Logger
import org.slf4j.LoggerFactory

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

private const val VERSION = 1.0

fun String.stripNum(): String {
    return this.replace(Regex("[0-9]"), "")
}



fun main(args: Array<String>) {
    var debug = false
    if (args.isNotEmpty()) {
        if (args[0].toLowerCase() == ("debug")) {
            debug = true
            (LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as ch.qos.logback.classic.Logger).level = Level.DEBUG
        }
    }
    val logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) //Obtain root level logger

    logger.info("PitCommander Server v$VERSION")

    //CONFIG
    logger.debug("Reading config from file 'config.json'")
    ActiveConfig.fromFile() //Read the config from the file
    logger.debug("Config loaded")

    //RUNTIME SETUP
    TimeTicker.setup(1000L, debug, 1489848500) //TODO REMOVE DEBUG TIME
    TbaPoller.setup(30000L)
    AnnounceSock.setup(5800)
    CommandSock.setup(5801)

    //RUNTIME START
    Thread(CommandSock, "sock.command").start()
    Thread(AnnounceSock, "sock.announce").start()
    Thread(TimeTicker, "runtime.ticker").start()
    Thread(TbaPoller, "runtime.poller").start()

    //CHECKLIST INIT
    MatchChecklistContainer.init()
    SafetyChecklistContainer.init()


    while (true) {
        try {
            Thread.sleep(1000L)
        } catch (e: Exception) {}
    }

}