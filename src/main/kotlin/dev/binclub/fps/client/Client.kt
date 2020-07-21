package dev.binclub.fps.client

import dev.binclub.fps.client.input.InputHandler
import dev.binclub.fps.client.logic.LogicHandler
import dev.binclub.fps.client.render.Renderer
import dev.binclub.fps.client.render.Renderer.IMGUI
import dev.binclub.fps.client.utils.MonitorUtils.findActiveMonitor
import dev.binclub.fps.client.utils.obj.ObjLoader
import dev.binclub.fps.shared.entity.component.PositionedEntity
import dev.binclub.fps.shared.entity.impl.BlockEntity
import dev.binclub.fps.shared.entity.impl.LocalPlayerEntity
import dev.binclub.fps.shared.utils.setAssign
import dev.binclub.fps.shared.world.World
import glm_.vec2.Vec2i
import imgui.ImGui
import imgui.classes.Context
import imgui.impl.gl.ImplGL3
import imgui.impl.glfw.ImplGlfw
import org.lwjgl.opengl.GL
import uno.buffer.memFree
import uno.glfw.GlfwWindow
import uno.glfw.VSync
import uno.glfw.glfw

/**
 * @author cookiedragon234 04/Jul/2020
 */
object Client {
	lateinit var window: GlfwWindow
		private set
	lateinit var ctx: Context
		private set
	lateinit var implGlfw: ImplGlfw
		private set
	lateinit var implGl3: ImplGL3
		private set
	
	lateinit var world: World
	
	val camera = LocalPlayerEntity
	const val DEBUG = true
	
	fun main() {
		glfw {
			errorCallback = { error, description -> println("Glfw Error $error: $description") }
			init()
			windowHint {
				debug = DEBUG
				visible = false
				
				profile = uno.glfw.windowHint.Profile.core
				context.version = "3.2"
				forwardComp = true
			}
			
			val monitor = findActiveMonitor()
			val position = Vec2i(
				monitor.pos.x + (monitor.workArea.z / 2 - 1280 / 2),
				monitor.pos.y + (monitor.workArea.w / 2 - 720 / 2)
			)
			window = GlfwWindow(1280, 720, "FPS", null, position)
			window.makeContextCurrent()
			window.focusOnShow = true
			swapInterval = VSync.OFF
			
			window.show()
			
			InputHandler.setup()
		}
		GL.createCapabilities()
		
		
		if (IMGUI) {
			ctx = Context()
			ImGui.styleColorsDark()
			implGl3 = ImplGL3()
			implGlfw = ImplGlfw(window, true)
		}
		
		world = World()
		world.entities.add(LocalPlayerEntity)
		
		val mesh = ObjLoader.loadMesh("bunny")
		//mesh.texture = Texture.loadTexture("cube_texture.png")
		world.entities.add(BlockEntity(mesh).apply {
			injectComponent(PositionedEntity().apply {
				scale = 3f
				position.setAssign(0.15f, -0.15f, 0.15f)
			})
		})
		world.entities.add(BlockEntity(mesh).apply {
			injectComponent(PositionedEntity().apply {
				scale = 2f
				position.setAssign(0.15f, -0.15f, -0.15f)
			})
		})
		world.entities.add(BlockEntity(mesh).apply {
			injectComponent(PositionedEntity().apply {
				scale = 1f
				position.setAssign(-0.15f, -0.15f, -0.15f)
			})
		})
		world.entities.add(BlockEntity(mesh).apply {
			injectComponent(PositionedEntity().apply {
				scale = 2f
				position.setAssign(-0.15f, -0.15f, 0.15f)
			})
		})
		
		Renderer.setup()
		LogicHandler.setup()
		
		LogicHandler.startLoop()
		
		var exception: Throwable? = null
		try {
			window.cursorMode = GlfwWindow.CursorMode.disabled
			window.loop {
				try {
					InputHandler.performTick()
					Renderer.renderPass()
					
					Thread.yield()
				} catch (t: Throwable) {
					t.printStackTrace()
					window.shouldClose = true
				}
			}
			
			LogicHandler.stopLoop()
			
			finalize()
		} catch (t: Throwable) {
			exception = t
		}
		
		GL.setCapabilities(null)
		window.cursorMode = GlfwWindow.CursorMode.normal
		@Suppress("DEPRECATION") memFree(gln.buf)
		if (IMGUI) {
			implGl3.shutdown()
			implGlfw.shutdown()
			ctx.destroy()
		}
		window.destroy()
		glfw.terminate()
		GL.destroy()
		
		if (exception != null) {
			throw exception
		}
	}
	
	fun finalize() {
		InputHandler.finalize()
		Renderer.finalize()
		LogicHandler.finalize()
	}
}
