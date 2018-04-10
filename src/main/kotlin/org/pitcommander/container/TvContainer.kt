package org.pitcommander.container

/*
 * PCServer - Created on 11/12/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 11/12/17
 */

object TvContainer: Container() {
    override fun getName() = "Tv"

    enum class TvContent {
        STREAM,
        SCHEDULE,
        TIMER,
        SPECS,
        LOGO
    }

    class Tv {
        var power = false
        var volume = 75
        var muted = false
        var content = TvContent.SCHEDULE
    }

    //EXPOSED DATA
    private val tvs = hashMapOf<String, Tv>()


    init {
        tvs.put("Left", Tv())
        tvs.put("Right", Tv())
    }

    fun getTvs(): HashMap<String, Tv> {
        synchronized(lock) {
            return tvs
        }
    }

    fun containsTv(name: String): Boolean {
        synchronized(lock) {
            return tvs.containsKey(name)
        }
    }

    fun getContent(): List<TvContent> {
        synchronized(lock) {
            return TvContent.values().toList()
        }
    }

    fun togglePower(name: String): Boolean {
        synchronized(lock) {
            if (tvs.containsKey(name)) {
                val power = tvs[name]!!.power
                tvs[name]?.power = !power
                fireUpdate()
                return true
            } else {
                return false
            }
        }
    }

    fun setPower(name: String, power: Boolean): Boolean {
        synchronized(lock) {
            if (tvs.containsKey(name)) {
                if (tvs[name]!!.power != power) {
                    tvs[name]?.power = power
                    fireUpdate()
                }
                return true
            } else {
                return false
            }
        }
    }

    fun getPower(name: String): Boolean {
        synchronized(lock) {
            return tvs[name]?.power ?: false
        }
    }

    fun setVolume(name: String, volume: Int): Boolean {
        synchronized(lock) {
            if (tvs.containsKey(name)) {
                tvs[name]!!.muted = false
                if (tvs[name]!!.volume != volume) {
                    tvs[name]?.volume = volume
                    fireUpdate()
                }
                return true
            } else {
                return false
            }
        }
    }

    fun incrementVolume(name: String, amount: Int): Boolean {
        synchronized(lock) {
            if (tvs.containsKey(name)) {
                val oldVolume = tvs[name]!!.volume
                tvs[name]!!.volume += amount
                if (tvs[name]!!.volume > 100) {
                    tvs[name]!!.volume = 100
                }
                if (tvs[name]!!.volume < 0) {
                    tvs[name]!!.volume = 0
                }
                if (oldVolume != tvs[name]!!.volume) {
                    fireUpdate()
                }
                return true
            } else {
                return false
            }
        }
    }

    fun getVolume(name: String): Int {
        synchronized(lock) {
            return tvs[name]?.volume ?: 0
        }
    }

    fun toggleMute(name: String): Boolean {
        synchronized(lock) {
            if (tvs.containsKey(name)) {
                val oldMuted = tvs[name]!!.muted
                tvs[name]?.muted = !oldMuted
                fireUpdate()
                return true
            } else {
                return false
            }
        }
    }

    fun setMute(name: String, mute: Boolean): Boolean {
        synchronized(lock) {
            if (tvs.containsKey(name)) {
                if (tvs[name]!!.muted != mute) {
                    tvs[name]?.muted = mute
                    fireUpdate()
                }
                return true
            } else {
                return false
            }
        }
    }

    fun getMute(name: String): Boolean {
        synchronized(lock) {
            return tvs[name]?.muted ?: false
        }
    }

    fun setContent(name: String, content: String): Boolean {
        synchronized(lock) {
            if (tvs.containsKey(name)) {
                val newContent = TvContent.valueOf(content)
                if (tvs[name]!!.content != newContent) {
                    tvs[name]?.content = newContent
                    fireUpdate()
                }
                return true
            } else {
                return false
            }
        }
    }

    fun getContent(name: String): String {
        synchronized(lock) {
            return tvs[name]?.content?.toString() ?: ""
        }
    }
}