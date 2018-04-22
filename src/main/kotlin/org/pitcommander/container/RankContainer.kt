package org.pitcommander.container

import net.came20.tba4j.data.RankingItem
import org.pitcommander.stripFrc
import org.pitcommander.util.Sorters

object RankContainer: Container() {
    override fun getName() = "Rank"

    data class SchemaElement(val name: String,
                             val type: SchemaElementType) {
        enum class SchemaElementType {
            INT,
            DOUBLE
        }
    }

    data class RankElement(val rank: Int,
                           val team: String,
                           val name: String,
                           val gameData: List<Any>)

    private var schema = arrayListOf<SchemaElement>()
    private var rankings = arrayListOf<RankElement>()

    private fun itemizeSchema() = schema.map { it.name }

    fun setSchema(schema: ArrayList<SchemaElement>) {
        synchronized(lock) {
            if (this.schema != schema) {
                this.schema = schema
                fireUpdate()
            }
        }
    }

    fun getSchema(): List<SchemaElement> {
        synchronized(lock) {
            return schema
        }
    }

    fun setRankings(rankings: ArrayList<RankElement>) {
        synchronized(lock) {
            if (this.rankings != rankings) {
                this.rankings = rankings
                fireUpdate()
            }
        }
    }

    fun getRankings(): List<RankElement> {
        synchronized(lock) {
            return rankings
        }
    }

}