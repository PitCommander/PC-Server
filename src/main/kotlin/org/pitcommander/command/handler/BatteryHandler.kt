package org.pitcommander.command.handler

import org.pitcommander.command.Command
import org.pitcommander.command.Commands
import org.pitcommander.command.Replies
import org.pitcommander.container.BatteryContainer

/*
 * PCServer - Created on 6/18/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 6/18/17
 */

object BatteryHandler : Handler() {
    override fun handleImpl(command: Command) {
        when (command.id) {
            Commands.BATTERY_FETCH -> {
                reply = Replies.BATTERY_DATA
                payload.put("batteries", BatteryContainer.getBatteries())
            }
            Commands.BATTERY_GET_CURRENT -> {
                val name = command.payload["name"] as? String ?: ""
                if (BatteryContainer.containsBattery(name)) {
                    reply = Replies.BATTERY_CURRENT
                    payload.put("name", name)
                    payload.put("current", BatteryContainer.getBatteryCurrent(name))
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Unknown battery [$name]")
                }
            }
            Commands.BATTERY_GET_PERCENTAGE -> {
                val name = command.payload["name"] as? String ?: ""
                if (BatteryContainer.containsBattery(name)) {
                    reply = Replies.BATTERY_PERCENTAGE
                    payload.put("name", name)
                    payload.put("percentage", BatteryContainer.getBatteryPercentage(name))
                }
            }
        }
    }
}