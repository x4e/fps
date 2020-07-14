package dev.binclub.fps.client.utils.gl.font

import dev.binclub.fps.client.Client.window
import dev.binclub.fps.client.render.ProjectionHandler
import dev.binclub.fps.client.render.d3.Render3dManager
import dev.binclub.fps.client.utils.VertexArrayObject
import dev.binclub.fps.client.utils.VertexBuffer
import dev.binclub.fps.client.utils.gl.Texture
import dev.binclub.fps.client.utils.gl.font.FontLoader.fontShader
import dev.binclub.fps.client.utils.use
import glm_.f
import glm_.mat4x4.Mat4
import glm_.vec2.Vec2
import glm_.vec3.Vec3
import glm_.vec4.Vec4
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL40.*

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
	fun drawText(text: String, offset: Vec3, colour: Vec4 = Vec4(1f)): Vec3 {
		val window = window.size
		val ortho = ProjectionHandler.orthoMatrix(0f, window.x.toFloat(), window.y.toFloat(), 0f)
		fontShader.use {
			fontShader["colour"] = colour
			fontShader["texture_sampler"] = 0
			for (c in text) {
				chars[c]?.let { char ->
					fontShader["projection"] = ortho.translate(offset)
					offset.x += char.draw(this).x
				}
			}
		}
		offset.y += this.size
		return offset
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
