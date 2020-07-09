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
	
	inline override fun bind() {
		glBindVertexArray(id)
	}
	
	inline override fun unbind() {
		glBindVertexArray(0)
	}
	
	
	fun finalize() {
		glDeleteVertexArrays(id)
	}
}
