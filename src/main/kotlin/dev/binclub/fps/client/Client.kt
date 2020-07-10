package dev.binclub.fps.client

import dev.binclub.fps.client.input.InputHandler
import dev.binclub.fps.client.logic.LogicHandler
import dev.binclub.fps.client.render.Renderer
import dev.binclub.fps.client.utils.gl.Mesh
import dev.binclub.fps.client.utils.MonitorUtils.findActiveMonitor
import dev.binclub.fps.client.utils.gl.Texture
import dev.binclub.fps.client.utils.obj.ObjLoader
import dev.binclub.fps.shared.entity.component.PositionedEntity
import dev.binclub.fps.shared.entity.impl.BlockEntity
import dev.binclub.fps.shared.entity.impl.LocalPlayerEntity
import dev.binclub.fps.shared.utils.setAssign
import dev.binclub.fps.shared.world.World
import glm_.vec2.Vec2i
import imgui.DEBUG
import imgui.ImGui
import imgui.classes.Context
import imgui.impl.gl.ImplGL3
import imgui.impl.gl.glslVersion
import imgui.impl.glfw.ImplGlfw
import org.lwjgl.opengl.GL
import org.lwjgl.system.Platform
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
				
				glslVersion = when (Platform.get()) {
					Platform.MACOSX -> 150
					else -> 130
				}
			}
			
			val monitor = findActiveMonitor()
			val position = Vec2i(
				monitor.pos.x + (monitor.workArea.z / 2 - 1280 / 2),
				monitor.pos.y + (monitor.workArea.w / 2 - 720 / 2)
			)
			window = GlfwWindow(1280, 720, "FPS", null, position)
			window.makeContextCurrent()
			window.cursorMode = GlfwWindow.CursorMode.disabled
			window.focusOnShow = true
			swapInterval = VSync.OFF
			
			window.show()
			
			InputHandler.setup()
		}
		
		GL.createCapabilities()
		
		ctx = Context()
		
		ImGui.styleColorsDark()
		
		implGlfw = ImplGlfw(window, true)
		implGl3 = ImplGL3()
		
		world = World()
		world.entities.add(LocalPlayerEntity)
		
		val mesh = ObjLoader.loadMesh("bunny")
		println(mesh)
		//mesh.texture = Texture.loadTexture("cube_texture.png")
		world.entities.add(BlockEntity(mesh).apply {
			injectComponent(PositionedEntity().apply {
				scale = 3f
				position.setAssign(0f, -0.15f, -2f)
			})
		})
		world.entities.add(BlockEntity(mesh).apply {
			injectComponent(PositionedEntity().apply {
				scale = 2f
				position.setAssign(0.5f, -0.15f, -2f)
			})
		})
		world.entities.add(BlockEntity(mesh).apply {
			injectComponent(PositionedEntity().apply {
				scale = 1f
				position.setAssign(0f, -0.15f, -2.5f)
			})
		})
		world.entities.add(BlockEntity(mesh).apply {
			injectComponent(PositionedEntity().apply {
				scale = 2f
				position.setAssign(0.5f, -0.15f, -2.5f)
			})
		})
		
		Renderer.setup()
		LogicHandler.setup()
		
		LogicHandler.startLoop()
		
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
		
		implGl3.shutdown()
		implGlfw.shutdown()
		ctx.destroy()
		window.destroy()
		glfw.terminate()
	}
	
	fun finalize() {
		InputHandler.finalize()
		Renderer.finalize()
		LogicHandler.finalize()
	}
}
