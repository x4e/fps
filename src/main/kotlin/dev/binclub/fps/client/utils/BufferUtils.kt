@file:Suppress("NOTHING_TO_INLINE")

package dev.binclub.fps.client.utils

import glm_.L
import glm_.mat4x4.Mat4
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.lwjgl.system.Pointer
import sun.misc.Unsafe
import java.io.Closeable
import java.nio.Buffer
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

fun Array<Mat4>.buffer(): FloatBuffer = MemoryUtil.memAllocFloat(4 * 4 * this.size).also { buffer ->
	var offset = 0
	for (mat in this) {
		mat.to(buffer, offset)
		offset += 4*4
	}
}

abstract class Ptr(val addr: Long, val alignment: Int): Closeable {
	companion object {
		val UNSAFE = Unsafe::class.java.declaredFields.first { it.type == Unsafe::class.java }.let {
			it.isAccessible = true
			it.get(null)
		} as Unsafe
	}
	
	override fun close() = free()
	inline fun free() {
		MemoryUtil.nmemFree(addr)
	}
}

class BytePtr(addr: Long): Ptr(addr, java.lang.Byte.BYTES) {
	constructor(size: Int = 1): this(MemoryUtil.nmemAlloc(java.lang.Byte.BYTES * size.L))
	
	operator fun set(index: Int, value: Byte) = UNSAFE.putByte(addr + (index * alignment), value)
	operator fun get(index: Int) = UNSAFE.getByte(addr + (index * alignment))
	
	operator fun plus(i: Int) = addr + (i * alignment)
}

class ShortPtr(addr: Long): Ptr(addr, java.lang.Short.BYTES) {
	constructor(size: Int = 1): this(MemoryUtil.nmemAlloc(java.lang.Short.BYTES * size.L))
	
	operator fun set(index: Int, value: Short) = UNSAFE.putShort(addr + (index * alignment), value)
	operator fun get(index: Int) = UNSAFE.getShort(addr + (index * alignment))
	
	operator fun plus(i: Int) = addr + (i * alignment)
}

class IntPtr(addr: Long): Ptr(addr, Integer.BYTES) {
	constructor(size: Int = 1): this(MemoryUtil.nmemAlloc(java.lang.Integer.BYTES * size.L))
	
	operator fun set(index: Int, value: Int) = UNSAFE.putInt(addr + (index * alignment), value)
	operator fun get(index: Int) = UNSAFE.getInt(addr + (index * alignment))
	
	operator fun plus(i: Int) = addr + (i * alignment)
}

class LongPtr(addr: Long): Ptr(addr, java.lang.Long.BYTES) {
	constructor(size: Int = 1): this(MemoryUtil.nmemAlloc(java.lang.Long.BYTES * size.L))
	
	operator fun set(index: Int, value: Long) = UNSAFE.putLong(addr + (index * alignment), value)
	operator fun get(index: Int) = UNSAFE.getLong(addr + (index * alignment))
	
	operator fun plus(i: Int) = addr + (i * alignment)
}

class FloatPtr(addr: Long): Ptr(addr, java.lang.Float.BYTES) {
	constructor(size: Int = 1): this(MemoryUtil.nmemAlloc(java.lang.Float.BYTES * size.L))
	
	operator fun set(index: Int, value: Float) = UNSAFE.putFloat(addr + (index * alignment), value)
	operator fun get(index: Int) = UNSAFE.getFloat(addr + (index * alignment))
	
	operator fun plus(i: Int) = addr + (i * alignment)
}

class DoublePtr(addr: Long): Ptr(addr, java.lang.Double.BYTES) {
	constructor(size: Int = 1): this(MemoryUtil.nmemAlloc(java.lang.Double.BYTES * size.L))
	
	operator fun set(index: Int, value: Double) = UNSAFE.putDouble(addr + (index * alignment), value)
	operator fun get(index: Int) = UNSAFE.getDouble(addr + (index * alignment))
	
	operator fun plus(i: Int) = addr + (i * alignment)
}

class PointerPtr(addr: Long): Ptr(addr, Pointer.POINTER_SIZE) {
	constructor(size: Int = 1): this(MemoryUtil.nmemAlloc(Pointer.POINTER_SIZE * size.L))
	
	operator fun set(index: Int, value: Long) = UNSAFE.putLong(addr + (index * alignment), value)
	operator fun get(index: Int) = UNSAFE.getLong(addr + (index * alignment))
	
	operator fun plus(i: Int) = addr + (i * alignment)
}

fun memFree(vararg buffers: Buffer) = buffers.forEach(MemoryUtil::memFree)
