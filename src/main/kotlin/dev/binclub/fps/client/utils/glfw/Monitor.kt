package dev.binclub.fps.client.utils.glfw

import dev.binclub.fps.client.utils.IntPtr
import glm_.vec2.Vec2i
import glm_.vec4.Vec4i
import org.lwjgl.glfw.GLFW

/**
 * @author cookiedragon234 27/Jul/2020
 */
class Monitor (val handle: Long) {
	val pos: Vec2i
		get() = IntPtr(2).use { xPos ->
			GLFW.nglfwGetMonitorPos(handle, xPos + 0, xPos + 1)
			Vec2i(xPos[0], xPos[1])
		}
	
	val workArea: Vec4i
		get() = IntPtr(4).use { area ->
			GLFW.nglfwGetMonitorWorkarea(handle, area + 0, area + 1, area + 2, area + 3)
			Vec4i(area[0], area[1], area[2], area[3])
		}
}
