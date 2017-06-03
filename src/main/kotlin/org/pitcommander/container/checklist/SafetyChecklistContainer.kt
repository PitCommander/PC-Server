package org.pitcommander.container.checklist

import org.pitcommander.util.ChecklistPopulator

/*
 * PCServer - Created on 6/1/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 6/1/17
 */

object SafetyChecklistContainer : ChecklistContainerBase("Safety") {
    override fun init() {
        ChecklistPopulator.populateSafety(boxes)
        fireUpdate()
    }
}