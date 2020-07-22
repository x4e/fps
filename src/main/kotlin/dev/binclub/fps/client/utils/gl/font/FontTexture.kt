package dev.binclub.fps.client.utils.gl.font

import dev.binclub.fps.client.Client.window
import dev.binclub.fps.client.render.ProjectionHandler
import dev.binclub.fps.client.utils.VertexArrayObject
import dev.binclub.fps.client.utils.VertexBuffer
import dev.binclub.fps.client.utils.gl.Texture
import dev.binclub.fps.client.utils.gl.font.FontLoader.fontShader
import dev.binclub.fps.client.utils.gl.*
import dev.binclub.fps.client.utils.use
import glm_.vec2.Vec2
import glm_.vec3.Vec3
import glm_.vec4.Vec4
import kotlin.math.max

/**
 * @author cookiedragon234 10/Jul/2020
 */
class FontTexture(
	id: Int,
	val name: String,
	val size: Int,
	val style: String,
	val chars: Map<Char, CharacterTexture>
) : Texture(id, GL_TEXTURE_2D) {
	fun drawText(text: String, offset: Vec3, colour: Vec4 = Vec4(1f)): Vec3
		= drawText(text, offset, Vec3(1f), colour)
	
	fun drawText(text: String, offset: Vec3, scale: Number, colour: Vec4 = Vec4(1f)): Vec3
		= drawText(text, offset, Vec3(scale), colour)
	
	fun drawText(text: String, offset: Vec3, scale: Vec3 = Vec3(1f), colour: Vec4 = Vec4(1f)): Vec3 {
		val outOffset = Vec3(0f)
		val window = window.size
		val projection = ProjectionHandler.orthoMatrix(0f, window.x.toFloat(), window.y.toFloat(), 0f)
			.translateAssign(offset)
			.scaleAssign(scale)
		fontShader.use {
			fontShader["colour"] = colour
			fontShader["texture_sampler"] = 0
			for (c in text) {
				chars[c]?.let { char ->
					fontShader["projection"] = projection
					val add = char.draw(this)
					projection.translateAssign(add.x, 0f, 0f)
					outOffset.x += add.x
					outOffset.y = max(outOffset.y, add.y)
				}
			}
		}
		outOffset *= scale
		return outOffset
	}
	
	data class CharacterTexture (val char: Char, val vao: VertexArrayObject, val idxVbo: VertexBuffer, val size: Vec2, val position: Vec4) {
		fun draw(texture: FontTexture): Vec2 {
			vao.use {
				idxVbo.use {
					texture.bind()
					//glDrawArrays(GL_TRIANGLE_STRIP, 0, 4)
					glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0)
					texture.unbind()
				}
			}
			return size
		}
	}
}
