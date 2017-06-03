package org.pitcommander.container

import net.came20.tba4j.data.Match
import org.pitcommander.config.ActiveConfig
import org.pitcommander.container.checklist.MatchChecklistContainer
import org.pitcommander.runtime.GeneralExecutor
import org.pitcommander.runtime.TimeTicker
import org.pitcommander.util.MatchUtils
import org.pitcommander.util.Sorters
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

/*
 * PCServer - Created on 5/27/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 5/27/17
 */

object MatchContainer : Container() {
    override fun getName() = "Match"

    //EXPOSED DATA
    private var lastMatch: ScheduleElement? = null
    private var currentMatch: ScheduleElement? = null
    private var nextMatch: ScheduleElement? = null
    private var currentlyPlaying: ScheduleElement? = null
    private var schedule = mutableListOf<ScheduleElement>()
    private var wins = 0
    private var losses = 0
    private var ties = 0
    private var timeToZero = 0L

    //INTERNAL DATA
    @Transient private var matches = listOf<Match>()
    @Transient private var allMatches = listOf<Match>()
    @Transient private var iLastMatch: Match? = null
    @Transient private var iCurrentMatch: Match? = null
    @Transient private var iNextMatch: Match? = null
    @Transient private var iCurrentlyPlaying: Match? = null

    class ScheduleElement(match: Match?) {
        val matchNumber: String
        val predictedTime: Long
        val scheduledTime: Long
        val bumperColor: String
        val opponents = mutableListOf<Int>()
        val allies = mutableListOf<Int>()
        val winner: String
        val redScore: Int
        val blueScore: Int

        init {
            if (match != null) {
                matchNumber = "${match.compLevel.toUpperCase()} ${match.matchNumber}"
                predictedTime = match.predictedTime
                scheduledTime = match.time
                bumperColor = MatchUtils.getTeamAlliance(match, ActiveConfig.settings.teamNumber).toString()
                if (match.winningAlliance == "red") {
                    winner = "RED"
                } else if (match.winningAlliance == "blue") {
                    winner = "BLUE"
                } else {
                    winner = "TIE"
                }
                if (bumperColor == "RED") {
                    match.alliances.blue.teamKeys.forEach {
                        opponents.add(Integer.parseInt(it.replace("frc", "")))
                    }
                    match.alliances.red.teamKeys.forEach {
                        allies.add(Integer.parseInt(it.replace("frc", "")))
                    }
                } else if (bumperColor == "BLUE") {
                    match.alliances.red.teamKeys.forEach {
                        opponents.add(Integer.parseInt(it.replace("frc", "")))
                    }
                    match.alliances.blue.teamKeys.forEach {
                        allies.add(Integer.parseInt(it.replace("frc", "")))
                    }
                }
                redScore = match.alliances.red.score
                blueScore = match.alliances.blue.score
            } else {
                matchNumber = "?"
                predictedTime = 0L
                scheduledTime = 0L
                bumperColor = "?"
                winner = "?"
                redScore = 0
                blueScore = 0
            }
        }
    }

    init {
        GeneralExecutor.scheduleAtFixedRate(this::tickMatches, 0, 1, TimeUnit.SECONDS)
    }

    //OPERATIVE METHODS
    private fun tickMatches() { //Called by the general executor every second
        synchronized(lock) {
            if (recalc()) { //If there were changes
                fireUpdate() //Fire an update
            }
        }
    }

    fun updateMatchLists(newList: List<Match>, newAllList: List<Match>) {
        synchronized(lock) {
            matches = Sorters.sortMatches(newList)  //List of scheduled matches
            allMatches = Sorters.sortMatches(newAllList) //List of all matches
            schedule.clear() //Clear the current schedule
            wins = 0 //Reset the wins, losses, and ties
            losses = 0
            ties = 0
            matches.forEach { //Iterate the schedule
                schedule.add(ScheduleElement(it)) //Add the converted elements to the published schedule
                when (MatchUtils.getOutcome(it, ActiveConfig.settings.teamNumber)) { //Identify if we won/lost/tied each match
                    MatchUtils.Outcome.WIN -> wins++ //Iterate the counters accordingly
                    MatchUtils.Outcome.LOSS -> losses++
                    MatchUtils.Outcome.TIE -> ties++
                }
            }
            recalc() //Recalculate the schedule
            fireUpdate()
        }
    }

    private fun recalc(): Boolean {
        var changed = false //Keep track of our changes
        val currentTime = TimeTicker.getCurrentTime() //The current time is read once to prevent shift
        var lastMatchIndex = -1 //The indexes of the values we want to pull from the lists
        var currentMatchIndex = -1 //Storing these as indexes instead of values makes this much easier
        var nextMatchIndex = -1
        var currentlyPlayingIndex = -1

        for (i in 0..matches.size - 1) { //Iterate the matches
            if (matches[i].time >= currentTime && currentMatchIndex == -1) { //If this match occurs after or on the current time (and it's not already set), it is the first current match
                currentMatchIndex = i //Set the current match index
                if (i >= 1) { //If there is data in the array before this index
                    lastMatchIndex = i - 1 //Set the last match to the index before the current match
                }
                if (matches.size > i + 1) { //If there is more data after this index
                    nextMatchIndex = i + 1 //Set the next match to the index after the current match
                }
                break
            }
        }

        for (i in 0..allMatches.size - 1) { //Iterate all the matches
            if (allMatches[i].time >= currentTime && currentlyPlayingIndex == -1) { //See above
                if (allMatches.size > 1) { //If there is more than 1 element in the array
                    currentlyPlayingIndex = i - 1 //Set t
                } else {
                    if (allMatches.size > 0) {
                        currentlyPlayingIndex = 0
                    }
                }
                break
            }
        }

        if (lastMatchIndex >= 0) {
            if (iLastMatch != matches[lastMatchIndex]) {
                iLastMatch = matches[lastMatchIndex]
                changed = true
            }
        } else {
            iLastMatch = null
        }

        if (currentMatchIndex >= 0) {
            if (iCurrentMatch != matches[currentMatchIndex]) {
                iCurrentMatch = matches[currentMatchIndex]
                changed = true
                MatchChecklistContainer.reset()
            }
        } else {
            iCurrentMatch = null
        }

        if (nextMatchIndex >= 0) {
            if (iNextMatch != matches[nextMatchIndex]) {
                iNextMatch = matches[nextMatchIndex]
                changed = true
            }
        } else {
            iNextMatch = null
        }
        
        if (currentlyPlayingIndex >= 0) {
            if (iCurrentlyPlaying != allMatches[currentlyPlayingIndex]) {
                iCurrentlyPlaying = allMatches[currentlyPlayingIndex]
                changed = true
            }
        } else {
            iCurrentlyPlaying = null
        }
        
        lastMatch = ScheduleElement(iLastMatch)
        currentMatch = ScheduleElement(iCurrentMatch)
        nextMatch = ScheduleElement(iNextMatch)
        currentlyPlaying = ScheduleElement(iCurrentlyPlaying)

        timeToZero = (iCurrentMatch?.time ?: 0L) - currentTime
        if (timeToZero < 0) {
            timeToZero = 0
        }
        
        return changed
    }

    //ACCESS METHODS
    fun getLastMatch(): ScheduleElement? {
        synchronized(lock) {
            return lastMatch
        }
    }

    fun getCurrentMatch(): ScheduleElement? {
        synchronized(lock) {
            return currentMatch
        }
    }

    fun getNextMatch(): ScheduleElement? {
        synchronized(lock) {
            return nextMatch
        }
    }

    fun getCurrentlyPlaying(): ScheduleElement? {
        synchronized(lock) {
            return currentlyPlaying
        }
    }

    fun getSchedule(): List<ScheduleElement>? {
        synchronized(lock) {
            return schedule
        }
    }

    fun getTimeToZero(): Long {
        synchronized(lock) {
            return timeToZero
        }
    }

    fun getWins(): Int {
        synchronized(lock) {
            return wins
        }
    }

    fun getLosses(): Int {
        synchronized(lock) {
            return losses
        }
    }

    fun getTies(): Int {
        synchronized(lock) {
            return ties
        }
    }
}