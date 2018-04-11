package org.pitcommander.command.handler

import org.pitcommander.command.Command
import org.pitcommander.command.Commands
import org.pitcommander.command.Replies
import org.pitcommander.command.Reply
import org.pitcommander.container.TvContainer
import org.pitcommander.socket.AnnounceSock

/*
 * PCServer - Created on 6/2/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 6/2/17
 */
object TvHandler : Handler() {
    override fun handleImpl(command: Command) {
        when (command.id) {
            Commands.TV_FETCH -> {
                reply = Replies.TV_DATA
                payload.put("tvs", TvContainer.getTvs())
            }
            Commands.TV_GET_STATES -> {
                reply = Replies.TV_STATES
                payload.put("states", TvContainer.getContent())
            }
            Commands.TV_POWER_TOGGLE -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (TvContainer.togglePower(name)) {
                        reply = Replies.GENERAL_ACK
                        payload.put("message", "TV '$name' power toggled!")
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "TV '$name' not found!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.TV_POWER_SET -> {
                val name = command.payload["name"] as? String
                val power = command.payload["power"] as? Boolean
                if (name != null && power != null) {
                    if (TvContainer.setPower(name, power)) {
                        reply = Replies.GENERAL_ACK
                        payload.put("message", "TV '$name' set to ${if (power) "on" else "off"}!")
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "TV '$name' not found!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.TV_POWER_GET -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (TvContainer.containsTv(name)) {
                        reply = Replies.TV_POWER
                        payload.put("name", name)
                        payload.put("power", TvContainer.getPower(name))
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "TV '$name' not found!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.TV_VOLUME_SET -> {
                val name = command.payload["name"] as? String
                val volume = command.payload["volume"] as? Double
                if (name != null && volume != null) {
                    if (TvContainer.setVolume(name, volume.toInt())) {
                        reply = Replies.GENERAL_ACK
                        payload.put("message", "TV '$name' set to volume '${volume.toInt()}'!")
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "TV '$name' not found!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.TV_VOLUME_INCREMENT -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (TvContainer.incrementVolume(name, +10)) {
                        reply = Replies.GENERAL_ACK
                        payload.put("message", "TV '$name' volume incremented!")
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "TV '$name' not found!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.TV_VOLUME_DECREMENT -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (TvContainer.incrementVolume(name, -10)) {
                        reply = Replies.GENERAL_ACK
                        payload.put("message", "TV '$name' volume decremented!")
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "TV '$name' not found!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.TV_VOLUME_GET -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (TvContainer.containsTv(name)) {
                        reply = Replies.TV_VOLUME
                        payload.put("name", name)
                        payload.put("volume", TvContainer.getVolume(name))
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "TV '$name' not found!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.TV_MUTE_TOGGLE -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (TvContainer.toggleMute(name)) {
                        reply = Replies.GENERAL_ACK
                        payload.put("message", "TV '$name' mute toggled!")
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "TV '$name' not found!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.TV_MUTE_SET -> {
                val name = command.payload["name"] as? String
                val mute = command.payload["mute"] as? Boolean
                if (name != null && mute != null) {
                    if (TvContainer.setMute(name, mute)) {
                        reply = Replies.GENERAL_ACK
                        payload.put("message", "TV '$name' ${if (mute) "muted" else "unmuted"}!")
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "TV '$name' not found!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.TV_MUTE_GET -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (TvContainer.containsTv(name)) {
                        reply = Replies.TV_MUTED
                        payload.put("name", name)
                        payload.put("muted", TvContainer.getMute(name))
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "TV '$name' not found!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.TV_CONTENT_SET -> {
                val name = command.payload["name"] as? String
                val content = command.payload["content"] as? String
                if (name != null && content != null) {
                    if (TvContainer.setContent(name, content)) {
                        reply = Replies.GENERAL_ACK
                        payload.put("message", "TV '$name' content set to '$content'!")
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "TV '$name' not found!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
            Commands.TV_CONTENT_GET -> {
                val name = command.payload["name"] as? String
                if (name != null) {
                    if (TvContainer.containsTv(name)) {
                        reply = Replies.TV_SELECTED
                        payload.put("name", name)
                        payload.put("content", TvContainer.getContent(name))
                    } else {
                        reply = Replies.GENERAL_FAIL
                        payload.put("message", "TV '$name' not found!")
                    }
                } else {
                    reply = Replies.GENERAL_FAIL
                    payload.put("message", "Invalid operation")
                }
            }
        }
    }
}