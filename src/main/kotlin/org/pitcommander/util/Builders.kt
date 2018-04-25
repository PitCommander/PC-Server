package org.pitcommander.util

import net.came20.tba4j.data.RankingItem
import net.came20.tba4j.data.RankingResponseObject
import net.came20.tba4j.data.RankingSortOrder
import net.came20.tba4j.data.Team
import org.pitcommander.container.RankContainer
import org.pitcommander.stripFrc

object Builders {
    fun buildStreamUrl(type: String, file: String, channel: String): String {
        if (type.toLowerCase() != "livestream") return channel
        return "https://livestream.com/accounts/$channel/events/$file/player"
    }

    fun buildRankingSchema(sortOrders: List<RankingSortOrder>): ArrayList<RankContainer.SchemaElement> {
        val list = arrayListOf<RankContainer.SchemaElement>()
        sortOrders.forEach {
            list.add(RankContainer.SchemaElement(it.name,
                    if (it.precision > 0) RankContainer.SchemaElement.SchemaElementType.DOUBLE
                    else RankContainer.SchemaElement.SchemaElementType.INT)
            )
        }
        return list
    }

    fun buildRankings(rankingsResponse: RankingResponseObject, teams: List<Team>): ArrayList<RankContainer.RankElement> {
        fun getTeamName(teamKey: String) = teams.firstOrNull { it.teamNumber == teamKey.stripFrc().toIntOrNull() }?.nickname ?: "ERROR"

        val list = arrayListOf<RankContainer.RankElement>()
        val rankings = Sorters.sortRankings(rankingsResponse.rankings)
        rankings.forEach {
            list.add(RankContainer.RankElement(
                    it.rank,
                    it.teamKey.stripFrc(),
                    getTeamName(it.teamKey),
                    "${it.record.wins}-${it.record.losses}-${it.record.ties}",
                    it.sortOrders.map { RankContainer.RankValueWrapper(it) }
            ))
        }
        return list
    }
}