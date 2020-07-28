package dev.binclub.fps.client.render

import dev.binclub.fps.client.options.GameSettings
import dev.binclub.fps.shared.entity.component.PositionedEntity
import glm_.f
import glm_.func.deg
import glm_.func.rad
import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import glm_.vec4.Vec4

/**
 * @author cookiedragon234 08/Jul/2020
 */
object ProjectionHandler {
	private const val Z_NEAR = 0.01f
	private const val Z_FAR = 100f
	
	val projection = glm.perspective(GameSettings.fov.rad, GameSettings.aspectRatio, Z_NEAR, Z_FAR)
	
	fun getModelMatrix(positioned: PositionedEntity, viewMatrix: Mat4): Mat4 =
		viewMatrix
			.translate(positioned.position)
			.rotateXassign(positioned.rotation.x.rad)
			.rotateYassign(positioned.rotation.y.rad)
			.rotateZassign(positioned.rotation.z.rad)
			.scaleAssign(positioned.scale)
	
	fun getViewMatrix(camera: PositionedEntity): Mat4 {
		val cameraPos = camera.position
		val rotation = camera.rotation
		
		return Mat4.identity
			.rotate(rotation.x.rad, Vec3(1, 0, 0))
			.rotateAssign(rotation.y.rad, Vec3(0, 1, 0))
			.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)
	}
	
	fun orthoMatrix(left: Float, right: Float, bottom: Float, top: Float) = glm.ortho(left, right, bottom, top)
}
