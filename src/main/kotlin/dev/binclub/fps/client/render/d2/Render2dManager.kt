package dev.binclub.fps.client.render.d2

import dev.binclub.fps.client.Client
import dev.binclub.fps.client.Client.DEBUG
import dev.binclub.fps.client.render.Renderer
import dev.binclub.fps.client.render.Renderer.IMGUI
import dev.binclub.fps.client.utils.*
import dev.binclub.fps.client.utils.gl.GlShader
import dev.binclub.fps.client.utils.gl.Mesh
import dev.binclub.fps.client.utils.gl.drawCalls
import dev.binclub.fps.client.utils.gl.font.FontLoader
import dev.binclub.fps.client.utils.gl.font.FontTexture
import dev.binclub.fps.client.utils.gl.triangles
import dev.binclub.fps.shared.entity.component.PositionedEntity
import dev.binclub.fps.shared.entity.impl.LocalPlayerEntity
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import glm_.vec4.Vec4
import imgui.ImGui
import imgui.WindowFlag
import org.lwjgl.opengl.GL40.*
import java.lang.String.format

/**
 * @author cookiedragon234 04/Jul/2020
 */
object Render2dManager {
	lateinit var shader: GlShader
		private set
	lateinit var font: FontTexture
		private set
	
	fun setup() {
		shader = GlShader.createShader("2d")
		font = FontLoader.loadFont("jetbrains_mono_regular_24")
	}
	
	fun renderPass() {
		glEnable(GL_DEPTH_TEST)
		glEnable(GL_BLEND)
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
		glEnable(GL_LINE_SMOOTH)
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST)
		
		shader.use {
			if (IMGUI) {
				ImGui.run {
					newFrame()
					
					begin("Debug", booleanArrayOf(true), WindowFlag.MenuBar.i)
					text("FPS: %.0f", io.framerate)
					val (pos, rot) = LocalPlayerEntity.requireComponent<PositionedEntity>().let {
						(it.position to it.rotation)
					}
					text("XYZ: %.1f %.1f %.1f", pos.x, pos.y, pos.z)
					text("PY: %.1f %.1f", rot.x, rot.y)
					end()
				}
			}
		}
		
		val offset = Vec3(0f)
		offset.y += font.drawText(format("FrameTime: %.2fms (%.0f fps)", Renderer.frameTime, Renderer.fps), offset, Vec4(1f, 0f, 0f, 0.5f)).y
		if (DEBUG) {
			offset.y += font.drawText(format("Draw Calls: %d", drawCalls), offset, 0.5f, Vec4(1f, 0f, 0f, 1f)).y
			offset.y += font.drawText(format("Triangles: %d", triangles), offset, 0.5f, Vec4(1f, 0f, 0f, 1f)).y
			
			drawCalls = 0
			triangles = 0
		}
		
		glDisable(GL_LINE_SMOOTH)
		glDisable(GL_BLEND)
		glDisable(GL_DEPTH_TEST)
	}
	
	fun finalize() {
	}
}
