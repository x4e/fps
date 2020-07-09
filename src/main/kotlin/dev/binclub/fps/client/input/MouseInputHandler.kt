package dev.binclub.fps.client.input

import cookiedragon.eventsystem.EventDispatcher.dispatch
import dev.binclub.fps.client.Client.window
import dev.binclub.fps.client.options.GameSettings
import glm_.vec2.Vec2
import glm_.vec3.Vec3
import org.lwjgl.glfw.GLFW.*
import uno.glfw.MouseButton

/**
 * @author cookiedragon234 09/Jul/2020
 */
object MouseInputHandler {
	lateinit var cursorPos: Vec2
	
	val states = HashMap<Int, Boolean>()
	
	fun setup() {
		for (button in MouseButton.values()) {
			states[button.i] = false
		}
		
		window.cursorPosCB = { pos ->
			if (!::cursorPos.isInitialized) {
				cursorPos = pos
			}
			
			val delta = pos - cursorPos
			
			val x = delta.x
			delta.x = delta.y
			delta.y = x
			
			delta *= GameSettings.sensitivity
			
			if (delta.anyNotEqual(0f)) {
				dispatch(MouseMoveEvent(delta))
			}
			
			cursorPos = pos
		}
		window.mouseButtonCB = { button: Int, action: Int, mods: Int ->
			when (action) {
				GLFW_PRESS -> {
					states[button] = true
				}
				GLFW_RELEASE -> {
					states[button] = false
				}
				GLFW_REPEAT -> {
					states[button] = true
				}
			}
			dispatch(MouseButtonEvent(button, action != GLFW_RELEASE, action == GLFW_REPEAT))
		}
	}
	
	fun performTick() {
	}
}

data class MouseButtonEvent(val button: Int, val pressed: Boolean, val repeated: Boolean)
data class MouseMoveEvent(val delta: Vec2)
