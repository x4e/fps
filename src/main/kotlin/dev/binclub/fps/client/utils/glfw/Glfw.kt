@file:Suppress("unused", "NOTHING_TO_INLINE", "SpellCheckingInspection")

import dev.binclub.fps.client.utils.IntPtr
import dev.binclub.fps.client.utils.LongPtr
import dev.binclub.fps.client.utils.PointerPtr
import dev.binclub.fps.client.utils.glfw.Monitor
import glm_.i
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_ANY_PROFILE
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.Pointer
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwSwapInterval
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryUtil
import org.lwjgl.system.MemoryUtil.NULL

/**
 * @author cookiedragon234 27/Jul/2020
 */

object glfw {
	inline fun init() = GLFW.glfwInit()
	inline fun terminate() = GLFW.glfwTerminate()
	
	inline fun pollEvents() = GLFW.glfwPollEvents()
	
	var errorCallback: ((Int, String) -> Unit)? = null
		set(value) {
			field = value
			if (value != null) {
				GLFW.glfwSetErrorCallback(GLFWErrorCallback.create { error, description ->
					value.invoke(error, MemoryUtil.memUTF8(description))
				})?.free()
			} else GLFW.nglfwSetErrorCallback(NULL)
		}
	
	/**
	 * Number of refreshes to wait before buffers are swapped aka vsync
	 */
	var swapInterval = 1
		set(value) {
			field = value
			glfwSwapInterval(value)
		}
	
	val hint = Hint
	object Hint {
		var debug = false
			set(value) {
				field = value
				GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, value.i)
			}
		
		var resizable = true
			set(value) {
				field = value
				GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, value.i)
			}
		
		var visible = true
			set(value) {
				field = value
				GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, value.i)
			}
		
		var profile = GLFW_OPENGL_ANY_PROFILE
			set(value) {
				field = value
				GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, value)
			}
		
		val context = Context
		object Context {
			var majorVersion = 0
				set(value) {
					field = value
					GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, value)
				}
			var minorVersion = 0
				set(value) {
					field = value
					GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, value)
				}
		}
		
		var forwardCompat = false
			set(value) {
				field = value
				GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, value.i)
			}
	}
	
	val monitors: Array<Monitor>
		get() = IntPtr(1).use { numMonitors ->
			val monArr = PointerPtr(GLFW.nglfwGetMonitors(numMonitors + 0))
			Array(numMonitors[0]) { i ->
				Monitor(monArr[i])
			}
		}
	
	val primaryMonitor
		get() = Monitor(GLFW.glfwGetPrimaryMonitor())
}
