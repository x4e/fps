package dev.binclub.fps.shared.entity.component

import dev.binclub.fps.shared.entity.Entity

/**
 * @author cookiedragon234 09/Jul/2020
 */
abstract class TickableEntity() : EntityComponent() {
	abstract fun tick()
}
