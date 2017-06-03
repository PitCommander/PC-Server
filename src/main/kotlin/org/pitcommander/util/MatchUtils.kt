package org.pitcommander.util

import net.came20.tba4j.data.Match
import org.pitcommander.util.MatchUtils.AllianceColor
import org.pitcommander.util.MatchUtils.Outcome





/*
 * PCServer - Created on 5/27/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 5/27/17
 */
object MatchUtils {
    enum class AllianceColor {
        RED,
        BLUE,
        NONE
    }

    enum class Outcome {
        WIN,
        LOSS,
        TIE,
        NONE
    }

    fun getTeamAlliance(m: Match, team: Int): AllianceColor {
        if (m.alliances.red.teamKeys != null && m.alliances.blue.teamKeys != null) {
            for (s in m.alliances.red.teamKeys) if (Integer.parseInt(s.replace("frc", "")) == team) return AllianceColor.RED
            for (s in m.alliances.blue.teamKeys) if (Integer.parseInt(s.replace("frc", "")) == team) return AllianceColor.BLUE
        }
        return AllianceColor.NONE
    }

    fun getOutcome(m: Match, team: Int): Outcome {
        if (m.alliances.red.score == 0 && m.alliances.blue.score == 0) {
            return Outcome.NONE
        }

        if (m.alliances.red.score == m.alliances.blue.score) {
            return Outcome.TIE
        }

        when (getTeamAlliance(m, team)) {
            AllianceColor.RED -> if (m.alliances.red.score > m.alliances.blue.score) {
                return Outcome.WIN
            }
            AllianceColor.BLUE -> if (m.alliances.blue.score > m.alliances.red.score) {
                return Outcome.WIN
            }
        }
        return Outcome.LOSS
    }
}