package dev.binclub.fps.client.utils

import gln.vertexArray.glVertexAttribPointer
import org.lwjgl.opengl.GL30.*
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * @author cookiedragon234 05/Jul/2020
 */
class VertexBuffer (
	buffer: FloatBuffer? = null,
	val type: Int = GL_ARRAY_BUFFER
): RenderBindable {
	constructor(): this(null)
	constructor(type: Int): this(null, type)
	
	var id: Int = -1
		private set
	
	init {
		id = glGenBuffers()
		
		if (buffer != null) {
			bind()
			bindData(buffer)
			unbind()
		}
	}
	
	inline fun bindData(buffer: FloatBuffer) = glBufferData(type, buffer, GL_STATIC_DRAW)
	inline fun bindData(buffer: IntBuffer) = glBufferData(type, buffer, GL_STATIC_DRAW)
	
	inline fun bindToVao(index: Int, size: Int, type: Int = GL_FLOAT) {
		glVertexAttribPointer(index, size, type, false, 0, 0)
	}
	
	override inline fun bind() {
		glBindBuffer(type, id)
	}
	
	override inline fun unbind() {
		glBindBuffer(type, 0)
	}
	
	
	fun finalize() {
		glDeleteBuffers(id)
	}
}
