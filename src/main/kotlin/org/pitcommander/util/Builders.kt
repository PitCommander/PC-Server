package org.pitcommander.util

import net.came20.tba4j.data.RankingItem
import net.came20.tba4j.data.RankingSortOrder
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
}