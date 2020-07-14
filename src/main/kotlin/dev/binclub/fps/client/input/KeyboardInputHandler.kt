package dev.binclub.fps.client.input

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.eventsystem.EventDispatcher.dispatch
import dev.binclub.fps.client.Client
import dev.binclub.fps.client.Client.window
import glm_.vec2.Vec2
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.*
import uno.glfw.GlfwWindow
import uno.glfw.Key

/**
 * @author cookiedragon234 09/Jul/2020
 */
object KeyboardInputHandler {
	val states = HashMap<Int, Boolean>()
	
	fun setup() {
		for (key in Key.values()) {
			states[key.i] = false
		}
		
		window.keyCB = { key: Int, scanCode: Int, action: Int, mods: Int ->
			when (action) {
				GLFW_PRESS -> {
					MouseInputHandler.states[key] = true
				}
				GLFW_RELEASE -> {
					MouseInputHandler.states[key] = false
				}
				GLFW_REPEAT -> {
					MouseInputHandler.states[key] = true
				}
			}
			if (key == Key.ESCAPE.i && action == GLFW_PRESS) {
				MouseInputHandler.skipMouseEvent = true
				if (window.cursorMode == GlfwWindow.CursorMode.disabled) {
					window.cursorMode = GlfwWindow.CursorMode.normal
				} else {
					window.cursorMode = GlfwWindow.CursorMode.disabled
				}
			}
			dispatch(KeyEvent(key, action != GLFW_RELEASE, action == GLFW_REPEAT))
		}
	}
	
	fun performTick() {
	}
}

data class KeyEvent(val key: Int, val pressed: Boolean, val repeated: Boolean)
