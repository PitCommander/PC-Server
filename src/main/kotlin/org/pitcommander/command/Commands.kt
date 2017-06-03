package org.pitcommander.command

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

enum class Commands(val group: CommandGroup) {

    //CHECKLIST_MATCH
    CHECKLIST_FETCH_MATCH(CommandGroup.CHECKLIST_MATCH),
    CHECKLIST_ADD_MATCH(CommandGroup.CHECKLIST_MATCH),
    CHECKLIST_REMOVE_MATCH(CommandGroup.CHECKLIST_MATCH),
    CHECKLIST_SET_MATCH(CommandGroup.CHECKLIST_MATCH),
    CHECKLIST_GET_MATCH(CommandGroup.CHECKLIST_MATCH),

    //CHECKLIST_SAFETY
    CHECKLIST_FETCH_SAFETY(CommandGroup.CHECKLIST_SAFETY),
    CHECKLIST_ADD_SAFETY(CommandGroup.CHECKLIST_SAFETY),
    CHECKLIST_REMOVE_SAFETY(CommandGroup.CHECKLIST_SAFETY),
    CHECKLIST_SET_SAFETY(CommandGroup.CHECKLIST_SAFETY),
    CHECKLIST_GET_SAFETY(CommandGroup.CHECKLIST_SAFETY),

    TV_SET(CommandGroup.TV)
}