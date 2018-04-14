package org.pitcommander.command.handler

import org.pitcommander.command.Command
import org.pitcommander.command.Commands
import org.pitcommander.command.Replies
import org.pitcommander.container.RankContainer

object RankHandler: Handler() {
    override fun handleImpl(command: Command) {
        when (command.id) {
            Commands.RANK_FETCH -> {
                reply = Replies.RANK_DATA
                payload.put("schema", RankContainer.getSchema())
                payload.put("rankings", RankContainer.getRankings())
            }
            Commands.RANK_SCHEMA_GET -> {
                reply = Replies.RANK_SCHEMA
                payload.put("schema", RankContainer.getSchema())
            }
            Commands.RANK_RANKINGS_GET -> {
                reply = Replies.RANK_RANKINGS
                payload.put("rankings", RankContainer.getRankings())
            }
        }
    }
}