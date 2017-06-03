package org.pitcommander.announcement

import org.pitcommander.container.Container

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

class ContainerUpdateAnnouncement(val container: Container) : Announcement {
    override fun getName(): String = container.getName() + "ContainerUpdate"
}