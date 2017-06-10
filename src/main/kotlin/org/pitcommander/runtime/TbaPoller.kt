package org.pitcommander.runtime

import net.came20.tba4j.TBA
import net.came20.tba4j.data.Event
import net.came20.tba4j.data.Match
import net.came20.tba4j.exception.TBAException
import org.pitcommander.config.ActiveConfig
import org.pitcommander.container.GeneralContainer
import org.pitcommander.container.MatchContainer
import org.pitcommander.stripNum
import org.pitcommander.util.Sorters
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.NoSuchElementException

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
object TbaPoller : Runnable {
    private val tba = TBA(ActiveConfig.settings.tbaApiKey)
    private var interval = 30000L
    private val teamNumber = ActiveConfig.settings.teamNumber

    private val logger = LoggerFactory.getLogger(javaClass)

    fun setup(interval: Long) {
        this.interval = interval
    }

    private var activeEvent = "" //The event code of the active event
    private var matches = listOf<Match>()
    private var allMatches = listOf<Match>()
    private val df = SimpleDateFormat("yyyy-MM-dd")


    override fun run() {
        while (!Thread.interrupted()) {
            logger.debug("Polling!")
            if (ActiveConfig.settings.eventCode.toLowerCase() != "dynamic") { //If the configured event is not "auto select"
                activeEvent = ActiveConfig.settings.eventCode //Set the event code
            } else { //The configuration calls for auto select
                val events = Sorters.sortEvents(tba.TeamRequests.getTeamEvents(teamNumber)) //Grab the team events
                try {
                    activeEvent = events.last { //Grab the last element in the list where
                        df.parse(it.startDate).before(Date()) //The current date is before the event start date
                    }.eventCode.stripNum() //Remove the year from the event code to make it compatible with tba4j
                } catch (e: NoSuchElementException) { //This is before the first event
                    activeEvent = events.first().eventCode.stripNum() //Mark the active event as the first event
                }
            }
            logger.debug("Found event: $activeEvent")
            GeneralContainer.setEvent(activeEvent) //Notify clients of the active event
            try {
                matches = tba.TeamRequests.getTeamMatches(teamNumber, activeEvent) //Pull team matches for the event
                allMatches = tba.EventRequests.getEventMatches(activeEvent) //Pull all matches for the event
                MatchContainer.updateMatchLists(matches, allMatches)
            } catch (e: TBAException) {
                e.printStackTrace()
            }
            logger.debug("Matches calculated")
            try {
                Thread.sleep(interval)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }
}