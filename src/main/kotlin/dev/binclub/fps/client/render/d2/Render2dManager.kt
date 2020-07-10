package dev.binclub.fps.client.render.d2

import dev.binclub.fps.client.render.Renderer.IMGUI
import dev.binclub.fps.client.utils.*
import dev.binclub.fps.client.utils.gl.GlShader
import dev.binclub.fps.client.utils.gl.Mesh
import dev.binclub.fps.shared.entity.component.PositionedEntity
import dev.binclub.fps.shared.entity.impl.LocalPlayerEntity
import imgui.ImGui
import imgui.WindowFlag

/**
 * @author cookiedragon234 04/Jul/2020
 */
object Render2dManager {
	lateinit var shader: GlShader
		private set
	
	fun setup() {
		shader = GlShader.createShader("2d")
	}
	
	fun renderPass() {
		shader.use {
			if (IMGUI) {
				ImGui.run {
					newFrame()
					
					begin("Debug", booleanArrayOf(true), WindowFlag.MenuBar.i)
					text("FPS: %.0f", io.framerate)
					val (pos, rot) = LocalPlayerEntity.requireComponent<PositionedEntity>().let {
						(it.position to it.rotation)
					}
					text("XYZ: %.1f %.1f %.1f", pos.x, pos.y, pos.z)
					text("PY: %.1f %.1f", rot.x, rot.y)
					end()
				}
			}
		}
	}
	
	fun finalize() {
	}
}
