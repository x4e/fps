@file:Suppress("UNCHECKED_CAST")

package dev.binclub.fps.shared.entity

import dev.binclub.fps.client.utils.gl.Mesh
import dev.binclub.fps.shared.entity.component.EntityComponent
import dev.binclub.fps.shared.utils.Result
import glm_.vec3.Vec3

/**
 * Something that can be stored in the world
 *
 * @author cookiedragon234 08/Jul/2020
 */
open class Entity {
	private val components = HashMap<Class<*>, MutableList<EntityComponent>>()
	
	fun injectComponent(component: EntityComponent) {
		component.entity = this
		
		var type: Class<*>? = component::class.java
		while (type != null) {
			components.getOrPut(type, { ArrayList(1) }).add(component)
			
			type = type.superclass
		}
	}
	
	inline fun <reified T: EntityComponent> component(op: (component: T) -> Unit): Result
		= component(T::class.java, op)
	
	inline fun <T: EntityComponent> component(type: Class<T>, op: (component: T) -> Unit): Result {
		findComponents(type)?.also {
			it.forEach(op)
			return Result.SUCCESS
		}
		return Result.FAILURE
	}
	
	inline fun <reified T: EntityComponent> requireComponent(): T = requireComponents(T::class.java).first()
	fun <T: EntityComponent> requireComponent(type: Class<T>): T = requireComponents(type).first()
	
	inline fun <reified T: EntityComponent> requireComponents(): List<T> = requireComponents(T::class.java)
	fun <T: EntityComponent> requireComponents(type: Class<T>): List<T> {
		return (components[type] ?: error("Component $type not applicable to $this")) as List<T>
	}
	
	inline fun <reified T: EntityComponent> findComponent(): T? = findComponents(T::class.java)?.firstOrNull()
	fun <T: EntityComponent> findComponent(type: Class<T>): T? = findComponents(type)?.firstOrNull()
	
	inline fun <reified T: EntityComponent> findComponents(): List<T>? = findComponents(T::class.java)
	fun <T: EntityComponent> findComponents(type: Class<T>): List<T>? {
		return components[type] as List<T>?
	}
	
	inline fun <reified T: EntityComponent> hasComponent(): Boolean = hasComponent(T::class.java)
	fun <T: EntityComponent> hasComponent(type: Class<T>): Boolean {
		components[type]?.let {
			return it.isNotEmpty()
		}
		return false
	}
}
