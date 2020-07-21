@file:Suppress("NOTHING_TO_INLINE")

package dev.binclub.fps.shared.utils

import glm_.glm
import glm_.mat4x4.Mat4
import glm_.pow
import glm_.vec3.Vec3
import kotlin.math.sqrt

/**
 * @author cookiedragon234 10/Jul/2020
 */
object Frustum {
	data class Plane (
		var a: Float = 0f,
		var b: Float = 0f,
		var c: Float = 0f,
		var d: Float = 0f
	) {
		/**
		 * When:
		 *     <0 point is in the negative halfspace
		 *     =0 point is on the plane
		 *     >0 point is in the positive halfspace
		 */
		fun distanceTo(point: Vec3)
			= a * point.x + b * point.y + c * point.z + d
		
		fun normalise() {
			val mag = sqrt(a.square() * b.square() * c.square() * d.square())
			a /= mag
			b /= mag
			c /= mag
			d /= mag
		}
		
		private inline fun Float.square() = this * this
	}
	
	val LEFT_PLANE = Plane()
	val RIGHT_PLANE = Plane()
	val TOP_PLANE = Plane()
	val BOTTOM_PLANE = Plane()
	val NEAR_PLANE = Plane()
	val FAR_PLANE = Plane()
	
	fun setPos(viewMatrix: Mat4) {
		LEFT_PLANE.a = viewMatrix.d0 + viewMatrix.a0
		LEFT_PLANE.b = viewMatrix.d1 + viewMatrix.a1
		LEFT_PLANE.c = viewMatrix.d2 + viewMatrix.a2
		LEFT_PLANE.d = viewMatrix.d3 + viewMatrix.a3
		
		RIGHT_PLANE.a = viewMatrix.d0 - viewMatrix.a0
		RIGHT_PLANE.b = viewMatrix.d1 - viewMatrix.a1
		RIGHT_PLANE.c = viewMatrix.d2 - viewMatrix.a2
		RIGHT_PLANE.d = viewMatrix.d3 - viewMatrix.a3
		
		TOP_PLANE.a = viewMatrix.d0 - viewMatrix.b0
		TOP_PLANE.b = viewMatrix.d1 - viewMatrix.b1
		TOP_PLANE.c = viewMatrix.d2 - viewMatrix.b2
		TOP_PLANE.d = viewMatrix.d3 - viewMatrix.b3
		
		BOTTOM_PLANE.a = viewMatrix.d0 + viewMatrix.b0
		BOTTOM_PLANE.b = viewMatrix.d1 + viewMatrix.b1
		BOTTOM_PLANE.c = viewMatrix.d2 + viewMatrix.b2
		BOTTOM_PLANE.d = viewMatrix.d3 + viewMatrix.b3
		
		NEAR_PLANE.a = viewMatrix.d0 + viewMatrix.c0
		NEAR_PLANE.b = viewMatrix.d1 + viewMatrix.c1
		NEAR_PLANE.c = viewMatrix.d2 + viewMatrix.c2
		NEAR_PLANE.d = viewMatrix.d3 + viewMatrix.c3
		
		FAR_PLANE.a = viewMatrix.d0 - viewMatrix.c0
		FAR_PLANE.b = viewMatrix.d1 - viewMatrix.c1
		FAR_PLANE.c = viewMatrix.d2 - viewMatrix.c2
		FAR_PLANE.d = viewMatrix.d3 - viewMatrix.c3
	}
	
	inline fun contains(point: Vec3) {
	
	}
}
