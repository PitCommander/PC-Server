package org.pitcommander.command

import org.pitcommander.command.handler.*


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
object CommandRouter {
    fun route(command: Command): Reply {
        return when (command.id.group) {
            CommandGroup.PING             -> PingHandler
            CommandGroup.CHECKLIST_MATCH  -> MatchChecklistHandler
            CommandGroup.CHECKLIST_SAFETY -> SafetyChecklistHandler
            CommandGroup.TV               -> TvHandler
            CommandGroup.GENERAL          -> GeneralHandler
            CommandGroup.MATCH            -> MatchHandler
            CommandGroup.BATTERY          -> BatteryHandler
            CommandGroup.RANK             -> RankHandler
        }.handle(command)
    }
}