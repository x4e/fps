@file:Suppress("NOTHING_TO_INLINE")

package dev.binclub.fps.shared.utils

import glm_.func.common.clamp
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import glm_.vec3.Vec3t
import org.lwjgl.system.CallbackI
import java.time.Duration
import java.time.Instant
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author cookiedragon234 05/Jul/2020
 */
inline fun measureTime(block: () -> Unit): Duration {
	val start = Instant.now()
	block()
	return Duration.between(start, Instant.now())
}

inline fun <reified B, reified T: Vec3t<B>> T.assign(other: T): T = this.apply {
	this.x = other.x
	this.y = other.y
	this.z = other.z
}

inline fun Vec3.clampOverflowAssign(min: Float, max: Float): Vec3 = this.apply {
	this.x = min + ((this.x - min) % max)
	this.y = min + ((this.y - min) % max)
	this.z = min + ((this.z - min) % max)
}

inline fun Vec3.clampAssign(min: Float, max: Float): Vec3 = this.apply {
	this.x = this.x.clamp(min, max)
	this.y = this.y.clamp(min, max)
	this.z = this.z.clamp(min, max)
}

inline fun Float.clampOverflow(min: Float, max: Float): Float = min + ((this - min) % max)

fun <A: Number, B: Vec3t<A>> B.setAssign(x: A, y: A, z: A): B = this.apply {
	this.x = x
	this.y = y
	this.z = z
}

operator fun Vec3.compareTo(other: Vec3): Int
	= when {
		this.x < other.x && this.y < other.y && this.z < other.z -> -1
		this.x > other.x && this.y > other.y && this.z > other.z -> 1
		else -> 0
	}

fun <T> Iterator<T>.any(op: (T) -> Boolean): Boolean {
	while (this.hasNext()) {
		if (op(this.next())) {
			return true
		}
	}
	return false
}
