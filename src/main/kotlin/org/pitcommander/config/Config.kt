package org.pitcommander.config

import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import java.io.File

/*
 * PCServer - Created on 5/26/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 5/26/17
 */

object ActiveConfig {
    var settings = ConfigRoot(); private set
    private val gson = GsonBuilder()
                        .setPrettyPrinting()
                        .setLenient()
                        .create()

    fun fromFile(fileName: String = "config.json") {
        val file = File(fileName)
        if (!file.exists()) {
            if (file.createNewFile()) {
                file.writeText(gson.toJson(settings))
            }
        } else {
            try {
                settings = gson.fromJson(file.readText(), ConfigRoot::class.java)
            } catch(e: Exception) {}
        }
    }

    fun toFile(fileName: String = "config.json") {
        val file = File(fileName)
        if (!file.exists()) {
            if (file.createNewFile()) {
                file.writeText(gson.toJson(settings))
            }
        } else {
            file.writeText(gson.toJson(settings))
        }
    }
}

data class ConfigRoot(val teamNumber: String = "0",
                      val eventCode: String = "dynamic",
                      val streamUrl: String = "",
                      val matchWarnTime: Long = 300,
                      val tbaApiKey: String = "",
                      val usePredictedTime: Boolean = false,
                      val matchChecklist: List<String> = listOf(),
                      val safetyChecklist: List<String> = listOf(),
                      val reflectorAddress: String = "",
                      val teamColor: String = "000000",
                      val teamLogo: String = "")

