package org.pitcommander.command.handler

import org.pitcommander.command.Command
import org.pitcommander.command.Commands
import org.pitcommander.command.Replies
import org.pitcommander.command.Reply
import org.pitcommander.config.ActiveConfig
import org.pitcommander.container.checklist.MatchChecklistContainer

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
object MatchChecklistHandler : Handler() {
    override fun handleImpl(command: Command) {
        when (command.id) {
            Commands.CHECKLIST_FETCH_MATCH -> {
                reply = Replies.CHECKLIST_DATA_MATCH
                payload.put("boxes", MatchChecklistContainer.boxes)
                payload.put("allChecked", MatchChecklistContainer.getAllChecked())
                payload.put("redSwitchTask", MatchChecklistContainer.getRedTask())
                payload.put("blueSwitchTask", MatchChecklistContainer.getBlueTask())
            }
            Commands.CHECKLIST_GET_MATCH -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (MatchChecklistContainer.containsCheckbox(name)) {
                        payload.put("name", name)
                        payload.put("value", MatchChecklistContainer.getChecked(name))
                        reply = Replies.CHECKLIST_VALUE_MATCH
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "Item '$name' does not exist!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.CHECKLIST_ADD_MATCH -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (MatchChecklistContainer.addCheckbox(name, false)) {
                        reply = Replies.GENERAL_SUCCESS
                        payload.put("message", "Item '$name' added successfully")
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "Item '$name' is already on the list!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.CHECKLIST_ADD_PERSISTENT_MATCH -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (MatchChecklistContainer.addCheckbox(name, false)) {
                        (ActiveConfig.settings.matchChecklist as MutableList).add(name)
                        reply = Replies.GENERAL_SUCCESS
                        payload.put("message", "Persistent item '$name' added successfully")
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "Item '$name' is already on the list!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.CHECKLIST_REMOVE_MATCH -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (MatchChecklistContainer.removeCheckbox(name)) {
                        reply = Replies.GENERAL_SUCCESS
                        payload.put("message", "Item '$name' removed successfully")
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "Item '$name' does not exist!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.CHECKLIST_SET_MATCH -> {
                val name = command.payload["name"] as? String
                val value = command.payload["value"] as? Boolean
                if (name != null && value != null) {
                    if (MatchChecklistContainer.setChecked(name, value)) {
                        reply = Replies.GENERAL_ACK
                        payload.put("message", "Item '$name' set to $value successfully")
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "Item '$name' does not exist!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
        }
    }
}