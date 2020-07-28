package dev.binclub.fps.client.render.d2

import dev.binclub.fps.client.Client
import dev.binclub.fps.client.Client.DEBUG
import dev.binclub.fps.client.render.Renderer
import dev.binclub.fps.client.utils.*
import dev.binclub.fps.client.utils.gl.GlShader
import dev.binclub.fps.client.utils.gl.drawCalls
import dev.binclub.fps.client.utils.gl.font.FontLoader
import dev.binclub.fps.client.utils.gl.font.FontTexture
import dev.binclub.fps.client.utils.gl.triangles
import glm_.vec3.Vec3
import glm_.vec4.Vec4
import org.lwjgl.opengl.GL40.*
import java.lang.String.format

/**
 * @author cookiedragon234 04/Jul/2020
 */
object Render2dManager {
	lateinit var shader: GlShader
		private set
	lateinit var font: FontTexture
		private set
	
	fun setup() {
		shader = GlShader.createShader("2d")
		font = FontLoader.loadFont("jetbrains_mono_regular_24")
	}
	
	fun renderPass() {
		glEnable(GL_BLEND)
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
		glEnable(GL_LINE_SMOOTH)
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST)
		
		shader.use {
			if (DEBUG) {
				val str1 = format("Draw Calls: %d", drawCalls)
				val str2 = format("Triangles: %d", triangles)
				
				val offset = Vec3(0f, Client.window.size.y - font.stringHeight(str1, 0.5f))
				offset.y -= font.drawText(str1, offset, 0.5f, Vec4(1f, 0f, 0f, 1f)).y
				offset.y -= font.drawText(str2, offset, 0.5f, Vec4(1f, 0f, 0f, 1f)).y
				val gpuAst = if (Renderer.waitingForGpuTimeResult) '*' else ' '
				offset.y -= font.drawText(format("GPU Time: %.2f$gpuAst", Renderer.gpuTime.get()), offset, 0.5f, Vec4(1f, 0f, 0f, 1f)).y
				offset.y -= font.drawText(format("CPU Time: %.2f", Renderer.cpuTime.get()), offset, 0.5f, Vec4(1f, 0f, 0f, 1f)).y
				
				drawCalls = 0
				triangles = 0
			}
		}
		
		glDisable(GL_LINE_SMOOTH)
		glDisable(GL_BLEND)
	}
	
	fun finalize() {
	}
}
