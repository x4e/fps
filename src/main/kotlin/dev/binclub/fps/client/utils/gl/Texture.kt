package dev.binclub.fps.client.utils.gl

import dev.binclub.fps.client.utils.RenderBindable
import dev.binclub.fps.client.utils.buffer
import gln.glf.semantic
import org.lwjgl.opengl.GL30.*
import org.lwjgl.stb.STBImage.*
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil
import uno.buffer.memFree
import java.io.File
import java.nio.ByteBuffer

/**
 * @author cookiedragon234 09/Jul/2020
 */
class Texture (
	val id: Int,
	val type: Int = GL_TEXTURE_2D
): RenderBindable {
	
	override inline fun bind() {
		glActiveTexture(GL_TEXTURE0)
		glBindTexture(type, id)
	}
	
	override inline fun unbind() {
		glBindTexture(type, 0)
	}
	
	
	companion object {
		fun loadTexture(name: String, type: Int = GL_TEXTURE_2D): Texture {
			var width = -1
			var height = -1
			var buff: ByteBuffer? = null
			
			stackPush().use { stack ->
				val wBuff = stack.mallocInt(1)
				val hBuff = stack.mallocInt(1)
				val channels = stack.mallocInt(1)
				
				val image =
					(
						Texture::class.java.getResourceAsStream("/textures/$name")
							?: error("Couldn't read texture $name")
					)
					.readBytes()
					.buffer()
				
				buff = stbi_load_from_memory(image, wBuff, hBuff, channels, 4)
					?: error("Couldn't load texture $name")
				
				memFree(image)
				
				width = wBuff.get(0)
				height = hBuff.get(0)
			}
			
			val id = glGenTextures()
			glBindTexture(type, id)
			glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
			glTexParameteri(type, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
			glTexParameteri(type, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
			glTexImage2D(type, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buff)
			glGenerateMipmap(type)
			
			stbi_image_free(buff!!)
			
			return Texture(id, type)
		}
	}
}
