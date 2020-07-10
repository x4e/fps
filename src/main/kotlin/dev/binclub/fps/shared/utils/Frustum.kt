package dev.binclub.fps.shared.utils

import glm_.glm
import glm_.mat4x4.Mat4

/**
 * @author cookiedragon234 10/Jul/2020
 */
object Frustum {
	enum class Planes {
		LEFT,
		RIGHT,
		BOTTOM,
		TOP,
		NEAR,
		FAR
	}
	
	fun setPos(viewMatrix: Mat4) {
	
	}
	
	fun isBoxVisible(box: BoundingBox) {
	
	}
}
