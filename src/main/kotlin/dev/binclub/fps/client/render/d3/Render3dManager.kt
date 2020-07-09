package dev.binclub.fps.client.render.d3

import dev.binclub.fps.client.Client
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
		shader.createUniform("projectionMatrix")
		shader.createUniform("worldMatrix")
		shader.createUniform("texture_sampler")
	}
	
	fun renderPass() {
		glEnable(GL_DEPTH_TEST)
		glEnable(GL_BLEND)
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
		if (DEBUG) checkError("renderLoop")
		
		val viewMatrix = ProjectionHandler.getViewMatrix(LocalPlayerEntity.requireComponent())
		
		shader.use {
			shader.setUniform("projectionMatrix", ProjectionHandler.projection)
			shader.setUniform("texture_sampler", 0)
			
			Client.world.entities.each { entity: Entity, meshed: MeshedEntity ->
				entity.component { positioned: PositionedEntity ->
					val matrix = ProjectionHandler.getModelMatrix(positioned, viewMatrix)
					shader.setUniform("worldMatrix", matrix)
				} `else` {
					shader.setUniform("worldMatrix", Mat4.identity)
				}
				meshed.mesh.draw()
			}
		}
		
		glDisable(GL_BLEND)
		glDisable(GL_DEPTH_TEST)
	}
	
	fun finalize() {
		shader.finalize()
	}
}
