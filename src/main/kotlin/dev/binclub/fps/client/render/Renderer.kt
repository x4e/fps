package dev.binclub.fps.client.render

import dev.binclub.fps.client.Client.DEBUG
import dev.binclub.fps.client.Client.window
import dev.binclub.fps.client.render.d2.Render2dManager
import dev.binclub.fps.client.render.d3.Render3dManager
import dev.binclub.fps.client.utils.gl.*
import dev.binclub.fps.shared.utils.AveragedDouble
import dev.binclub.fps.shared.utils.AveragedLong
import glm_.f
import glm_.vec2.Vec2i
import gln.checkError
import java.lang.String.format
import java.time.Duration
import java.time.Instant

/**
 * @author cookiedragon234 04/Jul/2020
 */
object Renderer {
	private var lastFrameTime = -1L
	var fps = 0f
	var frameTime = AveragedLong(60)
	
	var waitingForGpuTimeResult = false
	var cpuTime = AveragedDouble(60)
	var gpuTime = AveragedDouble(60)
	
	fun setup() {
		glClearColor(0f, 0f, 0.8f, 1f)
		Render2dManager.setup()
		Render3dManager.setup()
	}
	
	private var lastFrameBuffer: Vec2i? = null
	
	val queries = intArrayOf(
		0 // elapsed time
	).also { queries ->
		glGenQueries(queries)
	}
	
	fun renderPass() {
		if (!waitingForGpuTimeResult) {
			glBeginQuery(GL_TIME_ELAPSED, queries[0])
		}
		val cpuStart = Instant.now()
		
		val thisFrameBuffer = window.framebufferSize
		if (lastFrameBuffer == null || lastFrameBuffer != thisFrameBuffer) {
			glViewport(thisFrameBuffer)
			lastFrameBuffer = thisFrameBuffer
		}
		glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
		
		if (lastFrameTime > 0) {
			val duration = System.currentTimeMillis() - lastFrameTime
			frameTime.add(duration)
		}
		lastFrameTime = System.currentTimeMillis()
		fps = (1000f / frameTime.get()).f
		
		Render3dManager.renderPass()
		Render2dManager.renderPass()
		
		cpuTime.add(Duration.between(cpuStart, Instant.now()).toNanos() / 1000000.0)
		window.swapBuffers()
		
		if (!waitingForGpuTimeResult) {
			glEndQuery(GL_TIME_ELAPSED)
		}
		
		if (glGetQueryObjecti(queries[0], GL_QUERY_RESULT_AVAILABLE) == GL_TRUE) {
			waitingForGpuTimeResult = false
			gpuTime.add(glGetQueryObjectui64(queries[0], GL_QUERY_RESULT) / 1000000.0)
		} else {
			waitingForGpuTimeResult = true
		}
		
		if (DEBUG) checkError("renderLoop")
	}
	
	fun finalize() {
		Render2dManager.finalize()
		Render3dManager.finalize()
	}
}
