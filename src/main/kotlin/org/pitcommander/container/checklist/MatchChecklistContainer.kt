package org.pitcommander.container.checklist

import org.pitcommander.util.ChecklistPopulator

/*
 * PCServer - Created on 5/28/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 5/28/17
 */
object MatchChecklistContainer : ChecklistContainerBase("Match") {
    private var blueSwitchTask = false
    private var redSwitchTask = false

    override fun init() {
        ChecklistPopulator.populateMatch(boxes)
        fireUpdate()
    }

    override fun checkAllTasks() {
        blueSwitchTask = boxes.any { it.name == "Switch Bumpers: BLUE" && !it.value }
        redSwitchTask = boxes.any { it.name == "Switch Bumpers: RED" && !it.value}
    }

    override fun resetTasks() {
        ChecklistPopulator.populateMatch(boxes)
    }

    fun getBlueTask(): Boolean {
        synchronized(lock) {
            return blueSwitchTask
        }
    }

    fun getRedTask(): Boolean {
        synchronized(lock) {
            return redSwitchTask
        }
    }
}