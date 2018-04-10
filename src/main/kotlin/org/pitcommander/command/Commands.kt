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
    PING(CommandGroup.PING),

    //GENERAL
    GENERAL_FETCH(CommandGroup.GENERAL),
    GENERAL_GET_TEAM(CommandGroup.GENERAL),
    GENERAL_GET_EVENT(CommandGroup.GENERAL),
    GENERAL_GET_COLOR(CommandGroup.GENERAL),
    GENERAL_GET_LOGO(CommandGroup.GENERAL),

    //MATCH
    MATCH_FETCH(CommandGroup.MATCH),
    MATCH_GET_LAST(CommandGroup.MATCH),
    MATCH_GET_CURRENT(CommandGroup.MATCH),
    MATCH_GET_NEXT(CommandGroup.MATCH),
    MATCH_GET_PLAYING(CommandGroup.MATCH),
    MATCH_GET_SCHEDULE(CommandGroup.MATCH),
    MATCH_GET_RECORD(CommandGroup.MATCH),
    MATCH_GET_TTZ(CommandGroup.MATCH),

    //BATTERY
    BATTERY_FETCH(CommandGroup.BATTERY),
    BATTERY_GET_CURRENT(CommandGroup.BATTERY),
    BATTERY_SET_CURRENT(CommandGroup.BATTERY),
    BATTERY_GET_PERCENTAGE(CommandGroup.BATTERY),

    //CHECKLIST_MATCH
    CHECKLIST_FETCH_MATCH(CommandGroup.CHECKLIST_MATCH),
    CHECKLIST_ADD_MATCH(CommandGroup.CHECKLIST_MATCH),
    CHECKLIST_ADD_PERSISTENT_MATCH(CommandGroup.CHECKLIST_MATCH),
    CHECKLIST_REMOVE_MATCH(CommandGroup.CHECKLIST_MATCH),
    CHECKLIST_SET_MATCH(CommandGroup.CHECKLIST_MATCH),
    CHECKLIST_GET_MATCH(CommandGroup.CHECKLIST_MATCH),

    //CHECKLIST_SAFETY
    CHECKLIST_FETCH_SAFETY(CommandGroup.CHECKLIST_SAFETY),
    CHECKLIST_ADD_SAFETY(CommandGroup.CHECKLIST_SAFETY),
    CHECKLIST_ADD_PERSISTENT_SAFETY(CommandGroup.CHECKLIST_SAFETY),
    CHECKLIST_REMOVE_SAFETY(CommandGroup.CHECKLIST_SAFETY),
    CHECKLIST_SET_SAFETY(CommandGroup.CHECKLIST_SAFETY),
    CHECKLIST_GET_SAFETY(CommandGroup.CHECKLIST_SAFETY),

    //TV
    TV_FETCH(CommandGroup.TV),
    TV_GET_STATES(CommandGroup.TV),
    TV_POWER_TOGGLE(CommandGroup.TV),
    TV_POWER_SET(CommandGroup.TV),
    TV_POWER_GET(CommandGroup.TV),
    TV_VOLUME_SET(CommandGroup.TV),
    TV_VOLUME_INCREMENT(CommandGroup.TV),
    TV_VOLUME_DECREMENT(CommandGroup.TV),
    TV_VOLUME_GET(CommandGroup.TV),
    TV_MUTE_SET(CommandGroup.TV),
    TV_MUTE_TOGGLE(CommandGroup.TV),
    TV_MUTE_GET(CommandGroup.TV),
    TV_CONTENT_SET(CommandGroup.TV),
    TV_CONTENT_GET(CommandGroup.TV)
}