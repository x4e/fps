package dev.binclub.fps.client.utils

import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil.NULL
import uno.glfw.GlfwMonitor
import uno.glfw.MouseButton
import uno.glfw.glfw.monitors
import uno.glfw.glfw.primaryMonitor
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
	fun findActiveMonitor(): GlfwMonitor {
		val mousePos = MouseInfo.getPointerInfo().location
		
		for (monitorHandle in monitors) {
			val area = monitorHandle.workArea
			val posX = area.x
			val posY = area.y
			val widthX = area.z
			val widthY = area.w
			
			if (mousePos.x >= posX && mousePos.x <= (posX + widthX) && mousePos.y >= posY && mousePos.y <= (posY + widthY)) {
				return monitorHandle
			}
		}
		return primaryMonitor
	}
}
