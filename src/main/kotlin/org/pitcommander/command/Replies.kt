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
 * *
 * @version 5/24/17
 */
enum class Replies {
    NONE,
    GENERAL_ACK,
    GENERAL_FAIL,
    GENERAL_SUCCESS,
    PONG,

    //GENERAL CONTAINER
    GENERALC_DATA,
    GENERALC_TEAM,
    GENERALC_EVENT,
    GENERALC_COLOR,
    GENERALC_LOGO,
    GENERALC_STREAM,

    //MATCH
    MATCH_DATA,
    MATCH_LAST,
    MATCH_CURRENT,
    MATCH_NEXT,
    MATCH_PLAYING,
    MATCH_SCHEDULE,
    MATCH_RECORD,
    MATCH_TTZ,

    //BATTERY
    BATTERY_DATA,
    BATTERY_CURRENT,
    BATTERY_PERCENTAGE,

    //CHECKLIST_MATCH
    CHECKLIST_DATA_MATCH,
    CHECKLIST_VALUE_MATCH,

    //CHECKLIST_SAFETY
    CHECKLIST_DATA_SAFETY,
    CHECKLIST_VALUE_SAFETY,

    //TV
    TV_DATA,
    TV_STATES,
    TV_POWER,
    TV_VOLUME,
    TV_MUTED,
    TV_SELECTED
}
