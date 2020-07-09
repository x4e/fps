package dev.binclub.fps.shared.entity.component

import cookiedragon.eventsystem.EventDispatcher.register
import dev.binclub.fps.client.input.KeyEvent
import dev.binclub.fps.client.input.MouseMoveEvent
import dev.binclub.fps.shared.entity.Entity
import dev.binclub.fps.shared.utils.clampOverflowAssign
import glm_.d
import glm_.f
import glm_.func.cos
import glm_.func.rad
import glm_.func.sin
import uno.glfw.Key

/**
 * @author cookiedragon234 09/Jul/2020
 */
class ControllableEntity: TickableEntity() {
	private var strafeForwards = 0f
	private var strafeRight = 0f
	private var strafeBackwards = 0f
	private var strafeLeft = 0f
	
	override fun attachTo(entity: Entity) {
		val positioned = entity.requireComponent<PositionedEntity>() // only allow attaching to positioned entities
		register { event: MouseMoveEvent ->
			positioned.rotation.x += event.delta.x
			positioned.rotation.y += event.delta.y
			positioned.rotation.clampOverflowAssign(0f, 360f)
		}
		register { event: KeyEvent ->
			when (Key.of(event.key)) {
				Key.W -> strafeForwards = 1f
				Key.D -> strafeRight = 1f
				Key.S -> strafeBackwards = 1f
				Key.A -> strafeLeft = 1f
			}
		}
	}
	
	fun move(offsetX: Float, offsetY: Float, offsetZ: Float) {
		val positioned = entity.requireComponent<PositionedEntity>()
		if (offsetZ != 0f) {
			positioned.position.x += positioned.rotation.y.d.rad.sin.f * -1.0f * offsetZ
			positioned.position.z += positioned.rotation.y.d.rad.cos.f * offsetZ
		}
		if (offsetX != 0f) {
			positioned.position.x += (positioned.rotation.y - 90f).d.rad.sin.f * -1.0f * offsetX
			positioned.position.z += (positioned.rotation.y - 90f).d.rad.cos.f * offsetX
		}
		positioned.position.y += offsetY
	}
	
	override fun tick() {
	}
}
