package dev.binclub.fps.client.utils.gl

import dev.binclub.fps.client.utils.VertexArrayObject
import dev.binclub.fps.client.utils.VertexBuffer
import dev.binclub.fps.client.utils.buffer
import dev.binclub.fps.client.utils.use
import org.lwjgl.opengl.GL30.*
import uno.buffer.memFree

/**
 * @author cookiedragon234 04/Jul/2020
 */
class Mesh (positions: FloatArray, textures: FloatArray, indices: IntArray, val texture: Texture? = null) {
	val vao = VertexArrayObject()
	val posVbo = VertexBuffer(GL_ARRAY_BUFFER)
	val texVbo = VertexBuffer(GL_ARRAY_BUFFER)
	val idxVbo = VertexBuffer(GL_ELEMENT_ARRAY_BUFFER)
	val numVertices: Int = indices.size
	
	init {
		val posBuf = positions.buffer()
		val texBuf = textures.buffer()
		val idxBuf = indices.buffer()
		
		vao.use {
			posVbo.use {
				posVbo.bindData(posBuf)
				glEnableVertexAttribArray(0)
				posVbo.bindToVao(0, 3, GL_FLOAT)
			}
			
			texVbo.use {
				texVbo.bindData(texBuf)
				glEnableVertexAttribArray(1)
				texVbo.bindToVao(1, 2, GL_FLOAT)
			}
			
			idxVbo.use {
				idxVbo.bindData(idxBuf)
			}
		}
		
		memFree(posBuf, texBuf, idxBuf)
	}
	
	inline fun draw() {
		vao.use {
			idxVbo.use {
				texture?.bind()
				glDrawElements(GL_TRIANGLES, numVertices, GL_UNSIGNED_INT, 0)
				texture?.unbind()
			}
		}
	}
	
	fun finalize() {
		vao.finalize()
		texVbo.finalize()
		posVbo.finalize()
	}
}
