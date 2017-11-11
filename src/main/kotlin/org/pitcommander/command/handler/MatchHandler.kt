package org.pitcommander.command.handler

import org.pitcommander.command.Command
import org.pitcommander.command.Commands
import org.pitcommander.command.Replies
import org.pitcommander.container.MatchContainer

/*
 * PCServer - Created on 6/16/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 6/16/17
 */

object MatchHandler : Handler() {
    override fun handleImpl(command: Command) {
        when (command.id) {
            Commands.MATCH_FETCH -> {
                reply = Replies.MATCH_DATA
                payload.put("lastMatch", MatchContainer.getLastMatch())
                payload.put("currentMatch", MatchContainer.getCurrentMatch())
                payload.put("nextMatch", MatchContainer.getNextMatch())
                payload.put("currentlyPlaying", MatchContainer.getCurrentlyPlaying())
                payload.put("schedule", MatchContainer.getSchedule())
                payload.put("wins", MatchContainer.getWins())
                payload.put("losses", MatchContainer.getLosses())
                payload.put("ties", MatchContainer.getTies())
                payload.put("timeToZero", MatchContainer.getTimeToZero())
            }
            Commands.MATCH_GET_LAST -> {
                reply = Replies.MATCH_LAST
                payload.put("lastMatch", MatchContainer.getLastMatch())
            }
            Commands.MATCH_GET_CURRENT -> {
                reply = Replies.MATCH_CURRENT
                payload.put("currentMatch", MatchContainer.getCurrentMatch())
            }
            Commands.MATCH_GET_NEXT -> {
                reply = Replies.MATCH_NEXT
                payload.put("nextMatch", MatchContainer.getNextMatch())
            }
            Commands.MATCH_GET_PLAYING -> {
                reply = Replies.MATCH_PLAYING
                payload.put("currentlyPlaying", MatchContainer.getCurrentlyPlaying())
            }
            Commands.MATCH_GET_SCHEDULE -> {
                reply = Replies.MATCH_SCHEDULE
                payload.put("schedule", MatchContainer.getSchedule())
            }
            Commands.MATCH_GET_RECORD -> {
                reply = Replies.MATCH_RECORD
                payload.put("wins", MatchContainer.getWins())
                payload.put("losses", MatchContainer.getLosses())
                payload.put("ties", MatchContainer.getTies())
            }
            Commands.MATCH_GET_TTZ -> {
                reply = Replies.MATCH_TTZ
                payload.put("ttz", MatchContainer.getTimeToZero())
            }
        }
    }
}