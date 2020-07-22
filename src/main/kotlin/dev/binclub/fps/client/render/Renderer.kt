package dev.binclub.fps.client.render

import dev.binclub.fps.client.Client.implGl3
import dev.binclub.fps.client.Client.implGlfw
import dev.binclub.fps.client.Client.window
import dev.binclub.fps.client.render.d2.Render2dManager
import dev.binclub.fps.client.render.d3.Render3dManager
import dev.binclub.fps.client.utils.gl.*
import glm_.f
import glm_.vec2.Vec2i
import gln.checkError
import imgui.DEBUG
import imgui.ImGui

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
		glClearColor(0f, 0f, 0.8f, 1f)
		Render2dManager.setup()
		Render3dManager.setup()
	}
	
	private var lastFrameBuffer: Vec2i? = null
	
	fun renderPass() {
		val thisFrameBuffer = window.framebufferSize
		if (lastFrameBuffer == null || lastFrameBuffer != thisFrameBuffer) {
			glViewport(thisFrameBuffer)
			lastFrameBuffer = thisFrameBuffer
		}
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
	}
	
	fun finalize() {
		Render2dManager.finalize()
		Render3dManager.finalize()
	}
}
