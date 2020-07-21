@file:Suppress("OVERRIDE_BY_INLINE", "NOTHING_TO_INLINE")

package dev.binclub.fps.client.utils

import org.lwjgl.opengl.GL30.*

/**
 * @author cookiedragon234 05/Jul/2020
 */
class VertexArrayObject: RenderBindable {
	var id: Int
		private set
	
	init {
		id = glGenVertexArrays()
	}
	
	override inline fun bind() {
		glBindVertexArray(id)
	}
	
	override inline fun unbind() {
		glBindVertexArray(0)
	}
	
	
	fun cleanup() {
		glDeleteVertexArrays(id)
	}
}
