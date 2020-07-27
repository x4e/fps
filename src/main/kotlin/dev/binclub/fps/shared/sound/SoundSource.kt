package dev.binclub.fps.shared.sound

import dev.binclub.fps.shared.entity.Entity
import dev.binclub.fps.shared.entity.component.EntityComponent
import dev.binclub.fps.shared.entity.component.PositionedEntity

/**
 * @author cookiedragon234 17/Jul/2020
 */
class SoundSource: EntityComponent() {
	var playing = true
	var looping = false
	var volume = 1f
	var radius = 100f
}

class SoundSourceEntity: Entity() {
	init {
		injectComponent(PositionedEntity())
		injectComponent(SoundSource())
	}
}
