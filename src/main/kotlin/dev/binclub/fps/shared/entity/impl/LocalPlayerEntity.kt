package dev.binclub.fps.shared.entity.impl

import dev.binclub.fps.shared.entity.Entity
import dev.binclub.fps.shared.entity.component.CameraComponent
import dev.binclub.fps.shared.entity.component.ControllableEntity
import dev.binclub.fps.shared.entity.component.PositionedEntity
import glm_.vec3.Vec3

/**
 * @author cookiedragon234 09/Jul/2020
 */
object LocalPlayerEntity: Entity() {
	init {
		injectComponent(PositionedEntity(
			Vec3()
		))
		injectComponent(CameraComponent)
		injectComponent(ControllableEntity())
	}
}
