@file:Suppress("NOTHING_TO_INLINE")

package dev.binclub.fps.client.utils.gl

import dev.binclub.fps.client.utils.*
import glm_.vec3.Vec3
import gln.draw.glDrawElementsInstancedBaseVertex
import org.lwjgl.opengl.GL31.glDrawElementsInstanced

/**
 * @author cookiedragon234 04/Jul/2020
 */
class Mesh (positions: FloatArray, textures: FloatArray, normals: FloatArray, indices: IntArray, var texture: Texture? = null) {
	val vao = VertexArrayObject()
	val posVbo = VertexBuffer(GL_ARRAY_BUFFER)
	val texVbo = VertexBuffer(GL_ARRAY_BUFFER)
	val normVbo = VertexBuffer(GL_ARRAY_BUFFER)
	//val matrixVbo = VertexBuffer(GL_ARRAY_BUFFER)
	val idxVbo = VertexBuffer(GL_ELEMENT_ARRAY_BUFFER)
	val numVertices: Int = indices.size
	
	val color: Vec3? = null
	
	init {
		val posBuf = positions.buffer()
		val texBuf = textures.buffer()
		val normBuf = normals.buffer()
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
			
			normVbo.use {
				normVbo.bindData(normBuf)
				glEnableVertexAttribArray(2)
				texVbo.bindToVao(2, 3, GL_FLOAT)
			}
			
			//matrixVbo.use {
			//	glEnableVertexAttribArray(3)
			//	matrixVbo.bindToVao(3, 16, GL_FLOAT)
			//}
			
			idxVbo.use {
				idxVbo.bindData(idxBuf)
			}
		}
		
		memFree(posBuf, texBuf, normBuf, idxBuf)
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
	
	inline fun drawBatch(num: Int) {
		vao.use {
			idxVbo.use {
				texture?.bind()
				glDrawElementsInstanced(GL_TRIANGLES, numVertices, GL_UNSIGNED_INT, 0, num)
				texture?.unbind()
			}
		}
	}
	
	fun finalize() {
		vao.cleanup()
		texVbo.cleanup()
		posVbo.cleanup()
	}
	
	override fun toString(): String {
		return super.toString() + "[$numVertices vertices]"
	}
}
