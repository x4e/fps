package dev.binclub.fps.client.utils

import glm_.vec3.Vec3
import glm_.vec4.Vec4
import glm_.vec4.swizzle.xyz

/**
 * @author cookiedragon234 22/Jul/2020
 */
typealias RGBA = Vec4
typealias RGB = Vec3

val RED = RGBA(1f, 0f, 0f, 1f)

var RGBA.red
	get() = this.x
	set(value) { this.x = value }
var RGBA.green
	get() = this.y
	set(value) { this.y = value }
var RGBA.blue
	get() = this.z
	set(value) { this.z = value }
var RGBA.alpha
	get() = this.w
	set(value) { this.w = value }

var RGBA.rgb
	get() = this.xyz
	set(value) { this.xyz = value }

var RGB.red
	get() = this.x
	set(value) { this.x = value }
var RGB.green
	get() = this.y
	set(value) { this.y = value }
var RGB.blue
	get() = this.z
	set(value) { this.z = value }
