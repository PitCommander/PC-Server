package org.pitcommander.container

import kotlin.properties.Delegates

/*
 * PCServer - Created on 6/18/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 6/18/17
 */

object BatteryContainer : Container() {
    override fun getName() = "Battery"

    data class Battery(val name: String) {
        var current: Double by Delegates.observable(0.0) {
            _, _, new -> percentage = 100 - (new * 100.0 / 6.0).toInt()
        }
        var percentage: Int = 100 - (current * 100.0 / 6.0).toInt()
    }

    private val batteries = arrayListOf<Battery>()

    init {
        (1..12).mapTo(batteries) { Battery("Battery $it") }
    }

    fun getBatteryCurrent(name: String): Double {
        synchronized(lock) {
            return batteries.firstOrNull { it.name == name }?.current ?: 0.0
        }
    }

    fun getBatteryPercentage(name: String): Int {
        synchronized(lock) {
            return batteries.firstOrNull { it.name == name }?.percentage ?: 0
        }
    }

    fun setBatteryCurrent(name: String, current: Double): Boolean {
        synchronized(lock) {
            if (batteries.any { it.name == name }) {
                batteries.first { it.name == name }.current = current
                fireUpdate()
                return true
            } else {
                return false
            }
        }
    }

    fun getBatteries(): ArrayList<Battery> {
        synchronized(lock) {
            return batteries
        }
    }

    fun containsBattery(name: String): Boolean {
        synchronized(lock) {
            return batteries.any { it.name == name }
        }
    }
}