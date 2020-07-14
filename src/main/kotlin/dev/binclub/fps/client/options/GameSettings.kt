package dev.binclub.fps.client.options

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author cookiedragon234 08/Jul/2020
 */
object GameSettings {
	var fov: Int = 60
	var aspectRatio: Float = 1920f/1080f
	var sensitivity: Float = 0.2f
	
	val renderSettings = RenderSettings
}

open class GameSetting <T: Any?> (name: String, protected val dflt: T): ReadWriteProperty<Any, T> {
	override fun getValue(thisRef: Any, property: KProperty<*>): T = get()
	override fun setValue(thisRef: Any, property: KProperty<*>, value: T) { set(value) }
	
	protected var value: T = dflt
	protected val callbacks: MutableCollection<(value: T) -> Unit> = HashSet()
	
	fun get(): T = value
	
	fun set(`val`: T): T = this.value.also {
		this.value = `val`
	}
}
