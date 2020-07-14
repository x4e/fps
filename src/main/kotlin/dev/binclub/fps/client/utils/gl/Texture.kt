package dev.binclub.fps.client.utils.gl

import dev.binclub.fps.client.utils.RenderBindable
import dev.binclub.fps.client.utils.buffer
import glm_.f
import glm_.i
import glm_.vec2.Vec2
import org.lwjgl.opengl.GL30.*
import org.lwjgl.stb.STBImage.*
import org.lwjgl.system.MemoryStack.stackPush
import uno.buffer.memFree
import java.io.InputStream
import java.nio.ByteBuffer

/**
 * @author cookiedragon234 09/Jul/2020
 */
open class Texture (
	val id: Int,
	val type: Int = GL_TEXTURE_2D
): RenderBindable {
	
	override inline final fun bind() {
		glActiveTexture(GL_TEXTURE0)
		glBindTexture(type, id)
	}
	
	override inline final fun unbind() {
		glBindTexture(type, 0)
	}
	
	companion object {
		val bilinearFiltering = false // TODO: setting
		
		fun allocateTexture(name: String, type: Int = GL_TEXTURE_2D): Int
			= allocateTexture(
				Texture::class.java.getResourceAsStream("/textures/$name")
				?: error("Couldn't read texture $name"),
				type
			)
		
		fun allocateTexture(stream: InputStream, type: Int = GL_TEXTURE_2D): Int
			= allocateTextureDimensions(stream, type).first
		
		fun allocateTextureDimensions(stream: InputStream, type: Int = GL_TEXTURE_2D, channels: Int = 4, channelType: Int = GL_RGBA): Pair<Int, Vec2> {
			val dimensions = Vec2(-1f)
			var buff: ByteBuffer? = null
			
			stackPush().use { stack ->
				val wBuff = stack.mallocInt(1)
				val hBuff = stack.mallocInt(1)
				val outChannels = stack.mallocInt(1)
				
				val image = stream.readBytes().buffer()
				
				buff = stbi_load_from_memory(image, wBuff, hBuff, outChannels, channels)
					?: error("Couldn't load texture from $stream")
				
				memFree(image)
				
				dimensions.x = wBuff.get(0).f
				dimensions.y = hBuff.get(0).f
			}
			
			glPixelStorei(GL_UNPACK_ALIGNMENT, when (channelType) {
				GL_RGBA -> 4
				GL_RED -> 1
				GL_GREEN -> 1
				GL_BLUE -> 1
				GL_ALPHA -> 1
				else -> error("Unknown channel type [$channelType]")
			})
			val id = glGenTextures()
			glBindTexture(type, id)
			glTexParameteri(type, GL_TEXTURE_MIN_FILTER, if (bilinearFiltering) GL_LINEAR else GL_NEAREST)
			glTexParameteri(type, GL_TEXTURE_MAG_FILTER, if (bilinearFiltering) GL_LINEAR else GL_NEAREST)
			glTexImage2D(type, 0, channelType, dimensions.x.i, dimensions.y.i, 0, channelType, GL_UNSIGNED_BYTE, buff)
			glGenerateMipmap(type)
			glPixelStorei(GL_UNPACK_ALIGNMENT, 4)
			
			stbi_image_free(buff!!)
			return id to dimensions
		}
		
		fun loadTexture(name: String, type: Int = GL_TEXTURE_2D)
			= Texture(allocateTexture(name, type), type)
	}
}
