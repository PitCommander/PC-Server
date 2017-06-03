package org.pitcommander.announcement

import org.pitcommander.config.ActiveConfig
import org.pitcommander.container.MatchContainer

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
class TimeTickAnnouncement(val newTime: Long): Announcement {
    override fun getName() = "TimeTick"

    private val timeToZero = MatchContainer.getTimeToZero()
    private val warnTime = (timeToZero <= ActiveConfig.settings.matchWarnTime)
}