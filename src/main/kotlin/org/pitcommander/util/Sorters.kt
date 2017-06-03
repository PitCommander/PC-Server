package org.pitcommander.util

import net.came20.tba4j.data.Event
import net.came20.tba4j.data.Match
import java.text.SimpleDateFormat

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
object Sorters {
    private val df = SimpleDateFormat("yyyy-MM-dd")

    fun sortEvents(events: List<Event>): List<Event> {
        return events.sortedBy {
            df.parse(it.startDate)
        }
    }

    fun sortMatches(matches: List<Match>): List<Match> {
        return matches.sortedBy {
            it.time
        }
    }
}