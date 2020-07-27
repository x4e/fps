@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package dev.binclub.fps.client.input

import cookiedragon.eventsystem.EventDispatcher.dispatch
import dev.binclub.fps.client.Client.window
import dev.binclub.fps.client.options.GameSettings
import dev.binclub.fps.client.utils.glfw.MouseButton
import glm_.vec2.Vec2
import org.lwjgl.glfw.GLFW.*

/**
 * @author cookiedragon234 09/Jul/2020
 */
object MouseInputHandler {
	var cursorPos: Vec2 = Vec2(0f)
	var skipMouseEvent = true
	
	fun setup() {
		window.cursorPosCallback = { pos ->
			if (window.cursorMode == GLFW_CURSOR_DISABLED) {
				if (skipMouseEvent) {
					skipMouseEvent = false
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
		}
		MouseButton.callback { button, action ->
			window.cursorMode = GLFW_CURSOR_DISABLED
			
			dispatch(MouseButtonEvent(button.i, action != GLFW_RELEASE, action == GLFW_REPEAT))
		}
	}
	
	fun performTick() {
	}
}

data class MouseButtonEvent(val button: Int, val pressed: Boolean, val repeated: Boolean)
data class MouseMoveEvent(val delta: Vec2)
