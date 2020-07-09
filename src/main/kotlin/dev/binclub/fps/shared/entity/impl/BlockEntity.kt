package dev.binclub.fps.shared.entity.impl

import dev.binclub.fps.client.utils.gl.Mesh
import dev.binclub.fps.shared.entity.Entity
import dev.binclub.fps.shared.entity.component.MeshedEntity
import dev.binclub.fps.shared.entity.component.PositionedEntity
import glm_.vec3.Vec3

/**
 * @author cookiedragon234 09/Jul/2020
 */
class BlockEntity(mesh: Mesh): Entity() {
	init {
		injectComponent(MeshedEntity(mesh))
		injectComponent(PositionedEntity(
			Vec3(0, 0, -2),
			Vec3(1f),
			1f
		))
	}
}
