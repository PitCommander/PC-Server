package org.pitcommander

import ch.qos.logback.classic.Level
import com.google.gson.Gson
import org.pitcommander.command.Command
import org.pitcommander.command.handler.TvHandler
import org.pitcommander.config.ActiveConfig
import org.pitcommander.container.GeneralContainer
import org.pitcommander.container.MatchContainer
import org.pitcommander.container.TvContainer
import org.pitcommander.container.checklist.ChecklistContainerBase
import org.pitcommander.container.checklist.MatchChecklistContainer
import org.pitcommander.container.checklist.SafetyChecklistContainer
import org.pitcommander.runtime.GeneralExecutor
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

private const val VERSION = "1.4-SNAPSHOT"

fun String.stripNum(): String {
    return this.replace(Regex("[0-9]"), "")
}

fun String.stripFrc(): String {
    return this.replace("frc", "")
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
    TimeTicker.setup(1000L, debug, 1488642500) //TODO REMOVE DEBUG TIME
    TbaPoller.setup(30000L)
    AnnounceSock.setup(5800)
    CommandSock.setup(5801)

    //RUNTIME START
    val sockCommand = Thread(CommandSock, "sock.command").apply {start()}
    val sockAnnounce = Thread(AnnounceSock, "sock.announce").apply {start()}
    val runtimeTicker = Thread(TimeTicker, "runtime.ticker").apply {start()}
    val runtimePoller = Thread(TbaPoller, "runtime.poller").apply {start()}

    //CHECKLIST INIT
    MatchChecklistContainer.init()
    SafetyChecklistContainer.init()

    //CONTAINER INIT
    GeneralContainer.setTeamNumber(ActiveConfig.settings.teamNumber)
    TvContainer.init()

    keepAlive@ while (true) {
        when (readLine()?.toUpperCase()) {
            "Q" -> break@keepAlive
            "STREAM" -> TvContainer.setContent("Left", "STREAM")
            "SCHEDULE" -> TvContainer.setContent("Left", "SCHEDULE")
            "TIMER" -> TvContainer.setContent("Left", "TIMER")
            "SPECS" -> TvContainer.setContent("Left", "SPECS")
            "LOGO" -> TvContainer.setContent("Left", "LOGO")
        }
    }

    logger.info("Shutting down")

    ActiveConfig.toFile()

    runtimePoller.run {interrupt(); join()}
    runtimeTicker.run {interrupt(); join()}
    sockCommand.run {interrupt(); join()}
    sockAnnounce.run {interrupt(); join()}

    GeneralExecutor.shutdown()

}