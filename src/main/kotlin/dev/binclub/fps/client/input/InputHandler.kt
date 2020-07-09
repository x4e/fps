package dev.binclub.fps.client.input

import dev.binclub.fps.client.Client.window
import org.lwjgl.glfw.GLFW.*

/**
 * @author cookiedragon234 04/Jul/2020
 */
object InputHandler {
	fun setup() {
		MouseInputHandler.setup()
		KeyboardInputHandler.setup()
	}
	
	fun performTick() {
		MouseInputHandler.performTick()
		KeyboardInputHandler.performTick()
	}
	
	fun finalize() {
	}
}
