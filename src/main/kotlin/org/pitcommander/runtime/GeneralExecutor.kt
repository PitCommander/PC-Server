package org.pitcommander.runtime

import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.ThreadFactory

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
object GeneralExecutor : ScheduledThreadPoolExecutor(4)