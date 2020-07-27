@file:Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE", "MemberVisibilityCanBePrivate", "unused")

package dev.binclub.fps.client.utils.glfw

import dev.binclub.fps.client.utils.IntPtr
import dev.binclub.fps.client.utils.RenderBindable
import dev.binclub.fps.client.utils.gl.GL_FALSE
import dev.binclub.fps.client.utils.gl.GL_TRUE
import glm_.bool
import glm_.i
import glm_.value
import glm_.vec2.Vec2
import glm_.vec2.Vec2i
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWCursorPosCallback
import org.lwjgl.system.MemoryUtil.NULL

/**
 * @author cookiedragon234 27/Jul/2020
 */
class Window (name: String, size: Vec2i, monitor: Monitor? = null, share: Window? = null): RenderBindable {
	val handle: Long = glfwCreateWindow(size.x, size.y, name, monitor?.handle ?: NULL, share?.handle ?: NULL).also {
		if (it == 0L) {
			error("Couldnt create window $name")
		}
	}
	
	inline fun swapBuffers() = glfwSwapBuffers(handle)
	
	var shouldClose: Boolean
		get() = glfwWindowShouldClose(handle)
		set(value) = glfwSetWindowShouldClose(handle, value)
	
	var pos: Vec2i
		get() = IntPtr(2).use {
			nglfwGetWindowPos(handle, it + 0, it + 1)
			Vec2i(it[0], it[1])
		}
		set(value) {
			glfwSetWindowPos(handle, value.x, value.y)
		}
	
	var cursorPosCallback: (pos: Vec2) -> Unit = {}
		set(value) {
			field = value
			glfwSetCursorPosCallback(handle, GLFWCursorPosCallback.create { window, xPos, yPos ->
				field.invoke(Vec2(xPos, yPos))
			})
		}
	
	var size: Vec2i
		get() = IntPtr(2).use {
			nglfwGetWindowSize(handle, it + 0, it + 1)
			Vec2i(it[0], it[1])
		}
		set(value) {
			glfwSetWindowSize(handle, value.x, value.y)
		}
	
	val framebufferSize: Vec2i
		get() = IntPtr(2).use {
			nglfwGetFramebufferSize(handle, it + 0, it + 1)
			Vec2i(it[0], it[1])
		}
	
	/**
	 * GLFW_CURSOR_NORMAL makes the cursor visible and behaving normally
	 * GLFW_CURSOR_HIDDEN makes the cursor invisible when it is over the content area of the window but does not restrict the cursor from leaving
	 * GLFW_CURSOR_DISABLED hides and grabs the cursor, providing virtual and unlimited cursor movement. This is useful for implementing for example 3D camera
	 */
	var cursorMode: Int
		get() = glfwGetInputMode(handle, GLFW_CURSOR)
		set(value) = glfwSetInputMode(handle, GLFW_CURSOR, value)
	
	var rawMouse: Boolean
		get() = glfwGetInputMode(handle, GLFW_CURSOR) == GL_TRUE
		set(value) = glfwSetInputMode(handle, GLFW_CURSOR, rawMouse.i)
	
	val focused: Boolean
		get() = glfwGetWindowAttrib(handle, GLFW_FOCUSED).bool
	
	val iconified: Boolean
		get() = glfwGetWindowAttrib(handle, GLFW_ICONIFIED).bool
	
	val maximised: Boolean
		get() = glfwGetWindowAttrib(handle, GLFW_MAXIMIZED).bool
	
	val hovered: Boolean
		get() = glfwGetWindowAttrib(handle, GLFW_HOVERED).bool
	
	val visible: Boolean
		get() = glfwGetWindowAttrib(handle, GLFW_VISIBLE).bool
	
	var resizable: Boolean
		get() = glfwGetWindowAttrib(handle, GLFW_RESIZABLE).bool
		set(value) = glfwSetWindowAttrib(handle, GLFW_RESIZABLE, value.i)
	var decorated: Boolean
		get() = glfwGetWindowAttrib(handle, GLFW_DECORATED).bool
		set(value) = glfwSetWindowAttrib(handle, GLFW_DECORATED, value.i)
	
	var autoIconify: Boolean
		get() = glfwGetWindowAttrib(handle, GLFW_AUTO_ICONIFY).bool
		set(value) = glfwSetWindowAttrib(handle, GLFW_AUTO_ICONIFY, value.i)
	
	var floating: Boolean
		get() = glfwGetWindowAttrib(handle, GLFW_FLOATING).bool
		set(value) = glfwSetWindowAttrib(handle, GLFW_FLOATING, value.i)
	
	var focusOnShow: Boolean
		get() = glfwGetWindowAttrib(handle, GLFW_FOCUS_ON_SHOW).bool
		set(value) = glfwSetWindowAttrib(handle, GLFW_FOCUS_ON_SHOW, value.i)
	
	fun show() = glfwShowWindow(handle)
	fun hide() = glfwHideWindow(handle)
	
	fun destroy() {
		glfwDestroyWindow(handle)
	}
	
	override inline fun bind() {
		glfwMakeContextCurrent(handle)
	}
	
	override fun unbind() {
		glfwMakeContextCurrent(NULL)
	}
}
