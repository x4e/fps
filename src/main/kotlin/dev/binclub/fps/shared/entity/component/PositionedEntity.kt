package dev.binclub.fps.shared.entity.component

import dev.binclub.fps.shared.entity.Entity
import glm_.vec3.Vec3

/**
 * @author cookiedragon234 09/Jul/2020
 */
class PositionedEntity(
	val position: Vec3 = Vec3(0f),
	val rotation: Vec3 = Vec3(0f),
	var scale: Float = 1f
) : EntityComponent() {
}
