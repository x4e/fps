package dev.binclub.fps.client.utils

import dev.binclub.fps.client.utils.glfw.*
import java.awt.MouseInfo


/**
 * @author cookiedragon234 04/Jul/2020
 */
object MonitorUtils {
	/**
	 * Find the monitor containing the mouse cursor
	 *
	 * Due to lwjgl constraints this doesnt work if the cursor is on the task bar
	 */
	fun findActiveMonitor(): Monitor {
		val mousePos = MouseInfo.getPointerInfo().location
		
		for (monitor in glfw.monitors) {
			val area = monitor.workArea
			val posX = area.x
			val posY = area.y
			val widthX = area.z
			val widthY = area.w
			
			if (mousePos.x >= posX && mousePos.x <= (posX + widthX) && mousePos.y >= posY && mousePos.y <= (posY + widthY)) {
				return monitor
			}
		}
		return glfw.primaryMonitor
	}
}
