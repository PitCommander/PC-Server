package org.pitcommander.container

import org.pitcommander.announcement.ContainerUpdateAnnouncement
import org.pitcommander.socket.AnnounceSock

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

abstract class Container {
    abstract fun getName(): String
    @Transient protected val lock = Any()
    protected fun fireUpdate() = AnnounceSock.announce(ContainerUpdateAnnouncement(this))

}