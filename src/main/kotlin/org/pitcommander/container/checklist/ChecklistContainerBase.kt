package org.pitcommander.container.checklist

import org.pitcommander.container.Container
import org.pitcommander.util.ChecklistPopulator
import java.util.*

/*
 * PCServer - Created on 5/25/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 5/25/17
 */
open class ChecklistContainerBase(@Transient private val name: String): Container() {
    override fun getName() = "${name}Checklist"

    val boxes = Vector<Checkbox>()

    private var allChecked = false

    fun getAllChecked(): Boolean {
        synchronized(lock) {
            return allChecked
        }
    }

    protected fun checkAll() {
        allChecked = boxes.isNotEmpty() && boxes.all { it.value }
        checkAllTasks()
    }

    open fun checkAllTasks() { //Method to be overridden by subclasses to add additional checks to the "checkAll" block

    }

    open fun init() {}

    fun addCheckbox(name: String, value: Boolean): Boolean {
        val checkbox = Checkbox(name, value)
        synchronized(lock) {
            if (!boxes.contains(checkbox)) {
                boxes.add(checkbox)
                checkAll()
                fireUpdate()
                return true
            } else {
                return false
            }
        }
    }

    fun removeCheckbox(name: String): Boolean {
        synchronized(lock) {
            val result = boxes.removeIf {it.name == name}
            checkAll()
            fireUpdate()
            return result
        }
    }

    fun setChecked(name: String, value: Boolean): Boolean {
        synchronized(lock) {
            if (boxes.any {it.name == name}) {
                boxes[boxes.indexOfFirst {it.name == name}] = Checkbox(name, value)
                checkAll()
                fireUpdate()
                return true
            } else {
                return false
            }
        }
    }

    fun getChecked(name: String): Boolean {
        synchronized(lock) {
            if (boxes.any {it.name == name}) {
                return boxes.find {it.name == name}!!.value
            } else {
                return false
            }
        }
    }

    fun containsCheckbox(name: String): Boolean {
        synchronized(lock) {
            return boxes.any {
                it.name == name
            }
        }
    }

    fun reset() {
        synchronized(lock) {
            boxes.clear()
            resetTasks()
            checkAll()
            fireUpdate()
        }
    }

    open protected fun resetTasks() { //Method to be overridden to add custom tasks on reset

    }
}