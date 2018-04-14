package org.pitcommander.command.handler

import org.pitcommander.command.Command
import org.pitcommander.command.Commands
import org.pitcommander.command.Replies
import org.pitcommander.container.GeneralContainer

/*
 * PCServer - Created on 6/15/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 6/15/17
 */

object GeneralHandler : Handler() {
    override fun handleImpl(command: Command) {
        when (command.id) {
            Commands.GENERAL_FETCH -> {
                reply = Replies.GENERALC_DATA
                payload.put("teamNumber", GeneralContainer.getTeamNumber())
                payload.put("event", GeneralContainer.getEvent())
                payload.put("teamColor", GeneralContainer.getTeamColor())
                payload.put("teamLogo", GeneralContainer.getTeamLogo())
                payload.put("streamType", GeneralContainer.getStreamType())
                payload.put("streamVideo", GeneralContainer.getStreamVideo())
            }
            Commands.GENERAL_GET_TEAM -> {
                reply = Replies.GENERALC_TEAM
                payload.put("teamNumber", GeneralContainer.getTeamNumber())
            }
            Commands.GENERAL_GET_EVENT -> {
                reply = Replies.GENERALC_EVENT
                payload.put("event", GeneralContainer.getEvent())
            }
            Commands.GENERAL_GET_COLOR -> {
                reply = Replies.GENERALC_COLOR
                payload.put("teamColor", GeneralContainer.getTeamColor())
            }
            Commands.GENERAL_GET_LOGO -> {
                reply = Replies.GENERALC_LOGO
                payload.put("teamLogo", GeneralContainer.getTeamLogo())
            }
            Commands.GENERAL_GET_STREAM -> {
                reply = Replies.GENERALC_STREAM
                payload.put("streamType", GeneralContainer.getStreamType())
                payload.put("streamVideo", GeneralContainer.getStreamVideo())
            }
        }
    }

}