package dev.binclub.fps.shared.utils

import glm_.mat3x3.Mat3
import glm_.mat4x4.Mat4
import glm_.vec2.Vec2
import glm_.vec3.Vec3

/**
 * @author cookiedragon234 10/Jul/2020
 */
interface BoundingBox {
	fun intersects(other: BoundingBox): Boolean
	fun intersects(point: Vec3): Boolean
}

/**
 * This bounding box represents a 3d cuboid
 */
class BoundingCube(
	val min: Vec3,
	val max: Vec3
): BoundingBox {
	fun corners() = object: Iterator<Vec3> {
		var stage = 0
		
		override fun hasNext(): Boolean = stage < 8
		
		override fun next(): Vec3 {
			return when (stage) {
				0 -> min
				1 -> Vec3(max.x, min.y, min.z)
				2 -> Vec3(max.x, min.y, max.z)
				3 -> Vec3(min.x, min.y, max.z)
				4 -> max
				5 -> Vec3(min.x, max.y, max.z)
				6 -> Vec3(min.x, max.y, min.z)
				7 -> Vec3(max.x, max.y, min.z)
				else -> error("End of iterator")
			}.also {
				stage += 1
			}
		}
	}
	
	override fun intersects(other: BoundingBox): Boolean
		= when (other) {
			is BoundingCube -> (min >= other.min || min <= other.max) && (max >= other.min || max <= other.max)
			else -> error("Unsupported bounding box type ${other::class.java}")
		}
	
	override fun intersects(point: Vec3): Boolean {
		return (point >= min && point <= max)
	}
}
