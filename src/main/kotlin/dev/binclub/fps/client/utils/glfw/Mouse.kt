@file:Suppress("EnumEntryName", "unused")

package dev.binclub.fps.client.utils.glfw

import dev.binclub.fps.client.Client
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWMouseButtonCallback
import org.lwjgl.glfw.GLFWMouseButtonCallbackI

/**
 * @author cookiedragon234 27/Jul/2020
 */
enum class MouseButton(@JvmField val i: Int) {
	`1`(GLFW_MOUSE_BUTTON_1),
	`2`(GLFW_MOUSE_BUTTON_2),
	`3`(GLFW_MOUSE_BUTTON_3),
	`4`(GLFW_MOUSE_BUTTON_4),
	`5`(GLFW_MOUSE_BUTTON_5),
	`6`(GLFW_MOUSE_BUTTON_6),
	`7`(GLFW_MOUSE_BUTTON_7),
	`8`(GLFW_MOUSE_BUTTON_8),
	LAST(GLFW_MOUSE_BUTTON_LAST),
	LEFT(GLFW_MOUSE_BUTTON_LEFT),
	RIGHT(GLFW_MOUSE_BUTTON_RIGHT),
	MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE);
	
	var down = false
	
	companion object {
		init {
			glfwSetMouseButtonCallback(Client.window.handle, GLFWMouseButtonCallback.create { window, i, action, mods ->
				val mouse = MouseButton[i]
				mouse.down = action != GLFW_RELEASE
				callbacks.forEach { it.invoke(mouse, action) }
			})
		}
		
		val callbacks: MutableList<(key: MouseButton, action: Int) -> Unit> = arrayListOf()
		
		fun callback(cb: (button: MouseButton, action: Int) -> Unit) = cb.apply {
			callbacks.add(this)
		}
		
		operator fun get(i: Int) = values().firstOrNull { it.i == i } ?: error("No such mouse button $i")
	}
}
