package org.pitcommander.announcement

/*
 * PCServer - Created on 6/2/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 6/2/17
 */

class TvAnnouncement : Announcement, HashMap<String, Any>() {
    override fun getName() = "TV_SET"
}