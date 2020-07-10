package dev.binclub.fps.shared.utils

import glm_.mat3x3.Mat3
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3

/**
 * @author cookiedragon234 10/Jul/2020
 */
class BoundingBox(
	minX: Float,
	minY: Float,
	minZ: Float,
	maxX: Float,
	maxY: Float,
	maxZ: Float
) {
	val min = Vec3(minX, minY, minZ)
	val max = Vec3(maxX, maxY, maxZ)
	
	var minX: Float
		get() = min.x
		set(value) { min.x = value }
	var minY: Float
		get() = min.y
		set(value) { min.y = value }
	var minZ: Float
		get() = min.z
		set(value) { min.z = value }
	var maxX: Float
		get() = max.x
		set(value) { max.x = value }
	var maxY: Float
		get() = max.y
		set(value) { max.y = value }
	var maxZ: Float
		get() = max.z
		set(value) { max.z = value }
}
