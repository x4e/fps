package dev.binclub.fps.client.render.d2

import dev.binclub.fps.client.utils.*
import dev.binclub.fps.client.utils.gl.GlShader
import dev.binclub.fps.client.utils.gl.Mesh
import imgui.ImGui
import imgui.WindowFlag

/**
 * @author cookiedragon234 04/Jul/2020
 */
object Render2dManager {
	lateinit var shader: GlShader
		private set
	
	lateinit var mesh: Mesh
		private set
	
	fun setup() {
		shader = GlShader.createShader("2d")
		
		mesh = Mesh(
			floatArrayOf(
				// VO
				-0.5f, 0.5f, 0.5f,
				// V1
				-0.5f, -0.5f, 0.5f,
				// V2
				0.5f, -0.5f, 0.5f,
				// V3
				0.5f, 0.5f, 0.5f,
				// V4
				-0.5f, 0.5f, -0.5f,
				// V5
				0.5f, 0.5f, -0.5f,
				// V6
				-0.5f, -0.5f, -0.5f,
				// V7
				0.5f, -0.5f, -0.5f
			),
			floatArrayOf(
				0.5f, 0.0f, 0.0f,
				0.0f, 0.5f, 0.0f,
				0.0f, 0.0f, 0.5f,
				0.0f, 0.5f, 0.5f,
				0.5f, 0.0f, 0.0f,
				0.0f, 0.5f, 0.0f,
				0.0f, 0.0f, 0.5f,
				0.0f, 0.5f, 0.5f
			),
			intArrayOf(
				// Front face
				0, 1, 3, 3, 1, 2,
				// Top Face
				4, 0, 3, 5, 4, 3,
				// Right face
				3, 2, 7, 5, 3, 7,
				// Left face
				6, 1, 0, 6, 0, 4,
				// Bottom face
				2, 1, 6, 2, 6, 7,
				// Back face
				7, 6, 4, 7, 4, 5
			)
		)
	}
	
	fun renderPass() {
		shader.use {
			//mesh.draw()
			
			ImGui.run {
				newFrame()
				
				begin("", booleanArrayOf(true), WindowFlag.MenuBar.i)
				text("FPS: %.0f", io.framerate)
				end()
			}
		}
	}
	
	fun finalize() {
		mesh.finalize()
	}
}
