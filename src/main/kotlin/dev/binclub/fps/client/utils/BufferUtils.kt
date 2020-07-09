package dev.binclub.fps.client.utils

import org.lwjgl.BufferUtils
import org.lwjgl.system.MemoryUtil
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * @author cookiedragon234 08/Jul/2020
 */
fun FloatArray.buffer(): FloatBuffer =
	MemoryUtil.memAllocFloat(this.size).also {
		it.put(this).flip()
	}

fun IntArray.buffer(): IntBuffer =
	MemoryUtil.memAllocInt(this.size).also {
		it.put(this).flip()
	}

fun ByteArray.buffer(): ByteBuffer =
	MemoryUtil.memAlloc(this.size).also {
		it.put(this).flip()
	}
