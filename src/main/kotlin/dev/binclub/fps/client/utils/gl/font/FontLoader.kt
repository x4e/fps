package dev.binclub.fps.client.utils.gl.font

import dev.binclub.fps.client.utils.VertexArrayObject
import dev.binclub.fps.client.utils.VertexBuffer
import dev.binclub.fps.client.utils.buffer
import dev.binclub.fps.client.utils.gl.GlShader
import dev.binclub.fps.client.utils.gl.Texture
import dev.binclub.fps.client.utils.use
import glm_.f
import glm_.parseFloat
import glm_.parseInt
import glm_.vec2.Vec2
import glm_.vec4.Vec4
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL40.*
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import uno.buffer.memFree
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Used for loading bitmap fonts.
 * I'm using https://github.com/andryblack/fontbuilder for this
 *
 * @author cookiedragon234 10/Jul/2020
 */
object FontLoader {
	val fontShader = GlShader.createShader("font")
	
	fun loadFont(name: String): FontTexture {
		val texStream = FontLoader::class.java.getResourceAsStream("/fonts/$name.PNG")
		val (tex, bitmapDimen) = Texture.allocateTextureDimensions(texStream, GL_TEXTURE_2D)
		
		val specStream = FontLoader::class.java.getResourceAsStream("/fonts/$name.xml")
		
		val document = DocumentBuilderFactory.newInstance()
			.newDocumentBuilder()
			.parse(specStream)
		
		val fontElement = document.documentElement
		
		val fontName = fontElement.getAttribute("family")
		val fontSize = fontElement.getAttribute("size").parseInt()
		val fontStyle = fontElement.getAttribute("style")
		val fontHeight = fontElement.getAttribute("height").parseFloat
		
		val charElements = document.getElementsByTagName("Char")
		
		val chars = HashMap<Char, FontTexture.CharacterTexture>(charElements.length)
		for ((i, element) in charElements) {
			val width = element.attributes.getNamedItem("width").textContent.parseFloat
			val char = element.attributes.getNamedItem("code").textContent[0]
			
			// If the char size is smaller than the font height then we need to shift it downwards
			// i.e. a "."
			val offset = element.attributes.getNamedItem("offset").textContent.split(' ').let {
				Vec2(it[0].parseFloat, it[1].parseFloat)
			}
			
			// The location of this character within the bitmap image
			val rect = element.attributes.getNamedItem("rect").textContent.split(' ').let {
				Vec4(it[0].parseFloat, it[1].parseFloat, it[2].parseFloat, it[3].parseFloat)
			}
			
			val vao = VertexArrayObject()
			val posVbo = VertexBuffer(GL_ARRAY_BUFFER)
			val texVbo = VertexBuffer(GL_ARRAY_BUFFER)
			val idxVbo = VertexBuffer(GL_ELEMENT_ARRAY_BUFFER)
			
			
			val posBuf = floatArrayOf(
				offset.x,           offset.y,          0f,
				offset.x,           offset.y + rect.w, 0f,
				offset.x + rect.z,  offset.y + rect.w, 0f,
				offset.x + rect.z,  offset.y,          0f
			).buffer()
			val texBuf = floatArrayOf(
				((rect.x) / bitmapDimen.x),             ((rect.y) / bitmapDimen.y),
				((rect.x) / bitmapDimen.x),             ((rect.y + rect.w) / bitmapDimen.y),
				((rect.x + rect.z) / bitmapDimen.x),    ((rect.y + rect.w) / bitmapDimen.y),
				((rect.x + rect.z) / bitmapDimen.x),    ((rect.y) / bitmapDimen.y)
			).buffer()
			val idxBuf = intArrayOf(
				0, 1, 3, 3, 1, 2
			).buffer()
			
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
			
			chars[char] = FontTexture.CharacterTexture(
				char,
				vao,
				idxVbo,
				Vec2(
					element.attributes.getNamedItem("width").textContent.parseFloat,
					fontHeight
				),
				rect
			)
		}
		
		return FontTexture(tex, fontName, fontSize, fontStyle, chars)
	}
	
	class FontStorageInfo {
	}
	
	operator fun NodeList.iterator(): Iterator<Pair<Int, Node>> {
		val list = this
		return object: Iterator<Pair<Int, Node>> {
			var index = 0
			
			override fun hasNext() = index < list.length
			override fun next() = (index to list.item(index)).also { index += 1 }
		}
	}
}
