package org.pitcommander.util

import org.pitcommander.config.ActiveConfig
import org.pitcommander.container.MatchContainer
import org.pitcommander.container.checklist.Checkbox


/*
 * PCServer - Created on 5/25/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 5/25/17
 */
object ChecklistPopulator {
    fun populateMatch(boxes: MutableList<Checkbox>) {
        //Register bumper switch tasks
        when (MatchContainer.getCurrentMatch()?.bumperColor?.toLowerCase()) {
            "red" -> {
                if (MatchContainer.getLastMatch()?.bumperColor?.toLowerCase() != "red") {
                    boxes.add(Checkbox("Switch Bumpers: RED", false))
                }
            }
            "blue" -> {
                if (MatchContainer.getLastMatch()?.bumperColor?.toLowerCase() != "blue") {
                    boxes.add(Checkbox("Switch Bumpers: BLUE", false))
                }
            }
        }

        //Register configured tasks
        ActiveConfig.settings.matchChecklist.forEach {
            boxes.add(Checkbox(it, false))
        }
    }

    fun populateSafety(boxes: MutableList<Checkbox>) {
        //Register configured tasks
        ActiveConfig.settings.safetyChecklist.forEach {
            boxes.add(Checkbox(it, false))
        }
    }
}