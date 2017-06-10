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
    var settings = ConfigRoot(0, "dynamic", 300, "", true, listOf(), listOf()); private set
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

data class ConfigRoot(val teamNumber: Int,
                      val eventCode: String,
                      val matchWarnTime: Long,
                      val tbaApiKey: String,
                      val usePredictedTime: Boolean,
                      val matchChecklist: List<String>,
                      val safetyChecklist: List<String>)