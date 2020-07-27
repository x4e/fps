@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package dev.binclub.fps.client.input

import cookiedragon.eventsystem.EventDispatcher.dispatch
import dev.binclub.fps.client.Client.window
import dev.binclub.fps.client.utils.glfw.Key
import org.lwjgl.glfw.GLFW.*

/**
 * @author cookiedragon234 09/Jul/2020
 */
object KeyboardInputHandler {
	fun setup() {
		Key.callback { key, action ->
			if (key.i == Key.ESCAPE.i && action == GLFW_PRESS) {
				MouseInputHandler.skipMouseEvent = true
				if (window.cursorMode == GLFW_CURSOR_DISABLED) {
					window.cursorMode = GLFW_CURSOR_NORMAL
				} else {
					window.cursorMode = GLFW_CURSOR_DISABLED
				}
			}
			dispatch(KeyEvent(key.i, action != GLFW_RELEASE, action == GLFW_REPEAT))
		}
	}
	
	fun performTick() {
	}
}

data class KeyEvent(val key: Int, val pressed: Boolean, val repeated: Boolean)
