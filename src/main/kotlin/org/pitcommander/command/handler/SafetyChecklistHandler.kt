package org.pitcommander.command.handler

import org.pitcommander.command.Command
import org.pitcommander.command.Commands
import org.pitcommander.command.Replies
import org.pitcommander.command.Reply
import org.pitcommander.container.checklist.SafetyChecklistContainer

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
object SafetyChecklistHandler : Handler() {
    override fun handleImpl(command: Command) {
        when (command.id) {
            Commands.CHECKLIST_FETCH_SAFETY -> {
                reply = Replies.CHECKLIST_DATA_SAFETY
                payload.put("boxes", SafetyChecklistContainer.boxes)
                payload.put("allChecked", SafetyChecklistContainer.getAllChecked())
            }
            Commands.CHECKLIST_GET_SAFETY -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (SafetyChecklistContainer.containsCheckbox(name)) {
                        payload.put("name", name)
                        payload.put("value", SafetyChecklistContainer.getChecked(name))
                        reply = Replies.CHECKLIST_VALUE_SAFETY
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "Item '$name' does not exist!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.CHECKLIST_ADD_SAFETY -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (SafetyChecklistContainer.addCheckbox(name, false)) {
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
            Commands.CHECKLIST_REMOVE_SAFETY -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (SafetyChecklistContainer.removeCheckbox(name)) {
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
            Commands.CHECKLIST_SET_SAFETY -> {
                val name = command.payload["name"] as? String
                val value = command.payload["value"] as? Boolean
                if (name != null && value != null) {
                    if (SafetyChecklistContainer.setChecked(name, value)) {
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