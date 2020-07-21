@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package dev.binclub.fps.client.logic

import dev.binclub.fps.client.Client
import dev.binclub.fps.shared.entity.Entity
import dev.binclub.fps.shared.entity.component.TickableEntity
import java.lang.Thread.sleep
import java.lang.Thread.yield
import java.time.Duration
import java.time.Instant
import kotlin.concurrent.thread

/**
 * @author cookiedragon234 04/Jul/2020
 */
object LogicHandler {
	fun setup() {
	}
	
	val thread = thread(start = false, isDaemon = true, name = "Client Logic", block = this::runTick)
	
	var tickMillis = 60/20f // 20tps
	
	var color = 0f
	var direction = 1
	
	fun runTick() {
		while (!thread.isInterrupted) {
			val start = Instant.now()
			
			color += direction * 0.001f
			if (color > 1) {
				color = 1.0f
				direction = -direction
			} else if (color < 0) {
				color = 0.0f
				direction = -direction
			}
			
			Client.world.entities.each { entity: Entity, tickable: TickableEntity ->
				tickable.tick()
			}
			
			val elapsed = Duration.between(start, Instant.now()).toMillis()
			if (elapsed < tickMillis) {
				try {
					sleep((tickMillis - elapsed).toLong())
				} catch (e: InterruptedException) {
					break
				}
			} else {
				yield()
			}
		}
	}
	
	fun startLoop() {
		thread.start()
	}
	
	fun stopLoop() {
		thread.interrupt()
	}
	
	fun finalize() {
	}
}
