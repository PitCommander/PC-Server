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

    //CHECKLIST_MATCH
    CHECKLIST_DATA_MATCH,
    CHECKLIST_VALUE_MATCH,

    //CHECKLIST_SAFETY
    CHECKLIST_DATA_SAFETY,
    CHECKLIST_VALUE_SAFETY
}
