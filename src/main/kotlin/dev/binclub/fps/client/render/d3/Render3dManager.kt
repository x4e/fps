package dev.binclub.fps.client.render.d3

import dev.binclub.fps.client.Client
import dev.binclub.fps.client.options.RenderSettings
import dev.binclub.fps.client.render.ProjectionHandler
import dev.binclub.fps.client.utils.gl.GlShader
import dev.binclub.fps.client.utils.use
import dev.binclub.fps.shared.entity.Entity
import dev.binclub.fps.shared.entity.component.MeshedEntity
import dev.binclub.fps.shared.entity.component.PositionedEntity
import dev.binclub.fps.shared.entity.impl.LocalPlayerEntity
import glm_.mat4x4.Mat4
import gln.checkError
import imgui.DEBUG
import org.lwjgl.opengl.GL11.*

/**
 * @author cookiedragon234 04/Jul/2020
 */
object Render3dManager {
	lateinit var shader: GlShader
		private set
	
	fun setup() {
		shader = GlShader.createShader("3d")
	}
	
	fun renderPass() {
		glEnable(GL_DEPTH_TEST)
		glEnable(GL_BLEND)
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
		glEnable(GL_LINE_SMOOTH)
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST)
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE)
		
		if (RenderSettings.vertexCulling) {
			glEnable(GL_CULL_FACE)
		}
		
		val viewMatrix = ProjectionHandler.getViewMatrix(LocalPlayerEntity.requireComponent())
		
		shader.use {
			shader["projectionMatrix"] = ProjectionHandler.projection
			shader["texture_sampler"] = 0
			
			Client.world.entities.each { entity: Entity, meshed: MeshedEntity ->
				entity.component { positioned: PositionedEntity ->
					val matrix = ProjectionHandler.getModelMatrix(positioned, viewMatrix)
					shader["worldMatrix"] = matrix
				} `else` {
					shader["worldMatrix"] = Mat4.identity
				}
				val color = meshed.mesh.color
				if (color != null) {
					shader["color"] = color
					shader["useColor"] = true
				} else {
					shader["useColor"] = false
				}
				meshed.mesh.draw()
			}
		}
		
		
		if (RenderSettings.vertexCulling) {
			glDisable(GL_CULL_FACE)
		}
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL)
		glDisable(GL_LINE_SMOOTH)
		glDisable(GL_BLEND)
		glDisable(GL_DEPTH_TEST)
		
		if (DEBUG) checkError("renderLoop")
	}
	
	fun finalize() {
		shader.finalize()
	}
}
