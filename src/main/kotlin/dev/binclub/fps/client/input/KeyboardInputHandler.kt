package dev.binclub.fps.client.input

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.eventsystem.EventDispatcher.dispatch
import dev.binclub.fps.client.Client
import dev.binclub.fps.client.Client.window
import glm_.vec2.Vec2
import org.lwjgl.glfw.GLFW
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
				GLFW.GLFW_PRESS -> {
					MouseInputHandler.states[key] = true
				}
				GLFW.GLFW_RELEASE -> {
					MouseInputHandler.states[key] = false
				}
				GLFW.GLFW_REPEAT -> {
					MouseInputHandler.states[key] = true
				}
			}
			dispatch(KeyEvent(key, action != GLFW.GLFW_RELEASE, action == GLFW.GLFW_REPEAT))
		}
	}
	
	fun performTick() {
	}
}

data class KeyEvent(val key: Int, val pressed: Boolean, val repeated: Boolean)
