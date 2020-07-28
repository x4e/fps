package dev.binclub.fps.client

import dev.binclub.fps.client.input.InputHandler
import dev.binclub.fps.client.logic.LogicHandler
import dev.binclub.fps.client.render.Renderer
import dev.binclub.fps.client.utils.MonitorUtils.findActiveMonitor
import dev.binclub.fps.client.utils.al.*
import dev.binclub.fps.client.utils.glfw.Window
import dev.binclub.fps.client.utils.obj.ObjLoader
import dev.binclub.fps.shared.entity.component.PositionedEntity
import dev.binclub.fps.shared.entity.impl.BlockEntity
import dev.binclub.fps.shared.entity.impl.LocalPlayerEntity
import dev.binclub.fps.shared.utils.setAssign
import dev.binclub.fps.shared.world.World
import glm_.vec2.Vec2i
import org.lwjgl.glfw.GLFW
import org.lwjgl.openal.AL
import org.lwjgl.openal.ALC
import org.lwjgl.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER
import org.lwjgl.openal.ALC10.alcGetString
import org.lwjgl.openal.ALC10.alcMakeContextCurrent
import org.lwjgl.openal.ALC11
import org.lwjgl.opengl.GL


/**
 * @author cookiedragon234 04/Jul/2020
 */
object Client {
	lateinit var window: Window
		private set
	
	lateinit var world: World
	
	val camera = LocalPlayerEntity
	const val DEBUG = true
	
	fun main() {
		val defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER)
		val device: Long = ALC11.alcOpenDevice(defaultDeviceName)
		
		val attributes = intArrayOf(0)
		val alcontext: Long = ALC11.alcCreateContext(device, attributes)
		alcMakeContextCurrent(alcontext)
		
		val alcCapabilities = ALC.createCapabilities(device)
		val alCapabilities = AL.createCapabilities(alcCapabilities)
		
		//val dflt = alOpenDevice(alDefaultDevice())
		//alMakeContextCurrent(dflt)
		//alCreateCapabilities(dflt)
		println("Vendor: ${alGetVendor()}")
		println("Renderer: ${alGetRenderer()}")
		println("Version: ${alGetVersionString()}")
		println("Frequency: ${alGetFrequency(device)}")
		println("Refresh Rate: ${alGetRefreshRate(device)}")
		println("Default: $device")
		println("Available: ${alGetDevices()}")
		
		glfw.apply {
			init()
			
			errorCallback = { error, description ->
				println("Glfw Error $error: $description")
			}
			
			hint.apply {
				debug = DEBUG
				visible = false
				profile = GLFW.GLFW_OPENGL_CORE_PROFILE
				forwardCompat = true
				
				context.majorVersion = 3
				context.minorVersion = 2
			}
		}
		window = Window("FPS", Vec2i(1280, 720), null, null)
		
		window.bind()
		GL.createCapabilities()
		
		val monitor = findActiveMonitor()
		window.pos = Vec2i(
			monitor.pos.x + (monitor.workArea.z / 2 - 1280 / 2),
			monitor.pos.y + (monitor.workArea.w / 2 - 720 / 2)
		)
		glfw.swapInterval = 0 // no vsync
		
		window.show()
		InputHandler.setup()
		glfw.pollEvents()
		
		world = World()
		world.entities.add(LocalPlayerEntity)
		
		val mesh = ObjLoader.loadMesh("bunny")
		//mesh.texture = Texture.loadTexture("cube_texture.png")
		world.entities.add(BlockEntity(mesh).apply {
			injectComponent(PositionedEntity().apply {
				scale = 30f
				position.setAssign(1.5f, -0.15f, 1.5f)
			})
		})
		world.entities.add(BlockEntity(mesh).apply {
			injectComponent(PositionedEntity().apply {
				scale = 20f
				position.setAssign(1.5f, -0.15f, -1.5f)
			})
		})
		world.entities.add(BlockEntity(mesh).apply {
			injectComponent(PositionedEntity().apply {
				scale = 10f
				position.setAssign(-1.5f, -0.15f, -1.5f)
			})
		})
		world.entities.add(BlockEntity(mesh).apply {
			injectComponent(PositionedEntity().apply {
				scale = 20f
				position.setAssign(-1.5f, -0.15f, 1.5f)
			})
		})
		
		Renderer.setup()
		LogicHandler.setup()
		
		LogicHandler.startLoop()
		
		var exception: Throwable? = null
		try {
			while (!window.shouldClose) {
				try {
					InputHandler.performTick()
					Renderer.renderPass()
					
					Thread.yield()
				} catch (t: Throwable) {
					t.printStackTrace()
					window.shouldClose = true
				}
				glfw.pollEvents()
			}
			
			LogicHandler.stopLoop()
			
			finalize()
		} catch (t: Throwable) {
			exception = t
		}
		
		GL.setCapabilities(null)
		window.cursorMode = GLFW.GLFW_CURSOR_NORMAL
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
