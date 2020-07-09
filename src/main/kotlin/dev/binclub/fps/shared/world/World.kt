package dev.binclub.fps.shared.world

import dev.binclub.fps.shared.entity.Entity
import dev.binclub.fps.shared.entity.EntityList
import dev.binclub.fps.shared.entity.component.EntityComponent
import kotlin.reflect.KClass

/**
 * @author cookiedragon234 09/Jul/2020
 */
@Suppress("UNCHECKED_CAST")
class World {
	val entities = EntityList()
}
