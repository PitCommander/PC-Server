package org.pitcommander.container

/*
 * PCServer - Created on 6/4/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 6/4/17
 */

object GeneralContainer : Container() {
    override fun getName() = "General"

    private var teamNumber = 0
    private var event = "?"

    fun setTeamNumber(teamNumber: Int) {
        synchronized(lock) {
            if (this.teamNumber != teamNumber) {
                this.teamNumber = teamNumber
                fireUpdate()
            }
        }
    }

    fun setEvent(event: String) {
        synchronized(lock) {
            if (this.event != event) {
                this.event = event
                fireUpdate()
            }
        }
    }

    fun getTeamNumber(): Int {
        synchronized(lock) {
            return teamNumber
        }
    }

    fun getEvent(): String {
        synchronized(lock) {
            return event
        }
    }
}