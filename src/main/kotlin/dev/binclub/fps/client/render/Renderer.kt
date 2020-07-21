package dev.binclub.fps.client.render

import dev.binclub.fps.client.Client.implGl3
import dev.binclub.fps.client.Client.implGlfw
import dev.binclub.fps.client.Client.window
import dev.binclub.fps.client.logic.LogicHandler.color
import dev.binclub.fps.client.render.d2.Render2dManager
import dev.binclub.fps.client.render.d3.Render3dManager
import dev.binclub.fps.client.utils.gl.GlShader
import dev.binclub.fps.client.utils.gl.drawCalls
import dev.binclub.fps.client.utils.gl.font.FontLoader
import dev.binclub.fps.client.utils.gl.triangles
import dev.binclub.fps.client.utils.use
import glm_.f
import gln.checkError
import gln.glViewport
import imgui.DEBUG
import imgui.ImGui
import imgui.api.g
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import uno.glfw.glfw

/**
 * @author cookiedragon234 04/Jul/2020
 */
object Renderer {
	private var frameTimes = LongArray(60)
	private var frameTimeIndex = 0
	private var lastFrameTime = -1L
	var fps = 0f
	var frameTime: Double = 0.0
	
	const val IMGUI = false
	
	fun setup() {
		Render2dManager.setup()
		Render3dManager.setup()
	}
	
	fun renderPass() {
		
		glViewport(window.framebufferSize)
		glClearColor(color, color, color, 1f)
		glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
		
		if (lastFrameTime > 0) {
			val duration = System.currentTimeMillis() - lastFrameTime
			frameTimes[frameTimeIndex % 60] = duration
			frameTimeIndex += 1
		}
		lastFrameTime = System.currentTimeMillis()
		frameTime = frameTimes.average()
		fps = (1000f / frameTime).f
		
		if (IMGUI) {
			implGl3.newFrame()
			implGlfw.newFrame()
		}
		
		Render3dManager.renderPass()
		Render2dManager.renderPass()
		
		if (IMGUI) {
			ImGui.render()
			implGl3.renderDrawData(ImGui.drawData!!)
		}
		
		if (DEBUG) checkError("renderLoop")
		
		drawCalls = 0
		triangles = 0
	}
	
	fun finalize() {
		Render2dManager.finalize()
		Render3dManager.finalize()
	}
}
