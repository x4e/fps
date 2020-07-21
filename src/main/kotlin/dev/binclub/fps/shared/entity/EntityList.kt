@file:Suppress("UNCHECKED_CAST")

package dev.binclub.fps.shared.entity

import dev.binclub.fps.shared.entity.component.EntityComponent
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles

/**
 * @author cookiedragon234 09/Jul/2020
 */
class EntityList {
	val entities: MutableSet<Entity> = hashSetOf()
	
	fun add(entity: Entity) = entities.add(entity)
	
	
	inline fun <reified A: EntityComponent>
		each(noinline op: (entity: Entity, A) -> Unit)
		= eachEntityWithComponents(op, A::class.java as Class<EntityComponent>)
	
	inline fun <reified A: EntityComponent, reified B: EntityComponent>
		each(noinline op: (entity: Entity, A, B) -> Unit)
		= eachEntityWithComponents(op, A::class.java as Class<EntityComponent>, B::class.java as Class<EntityComponent>)
	
	inline fun <reified A: EntityComponent, reified B: EntityComponent, reified C: EntityComponent>
		each(noinline op: (entity: Entity, A, B, C) -> Unit)
		= eachEntityWithComponents(op, A::class.java as Class<EntityComponent>, B::class.java as Class<EntityComponent>, C::class.java as Class<EntityComponent>)
	
	inline fun <reified A: EntityComponent, reified B: EntityComponent, reified C: EntityComponent, reified D: EntityComponent>
		each(noinline op: (entity: Entity, A, B, C, D) -> Unit)
		= eachEntityWithComponents(op, A::class.java as Class<EntityComponent>, B::class.java as Class<EntityComponent>, C::class.java as Class<EntityComponent>, D::class.java as Class<EntityComponent>)
	
	inline fun <reified A: EntityComponent, reified B: EntityComponent, reified C: EntityComponent, reified D: EntityComponent, reified E: EntityComponent>
		each(noinline op: (entity: Entity, A, B, C, D, E) -> Unit)
		= eachEntityWithComponents(op, A::class.java as Class<EntityComponent>, B::class.java as Class<EntityComponent>, C::class.java as Class<EntityComponent>, D::class.java as Class<EntityComponent>, E::class.java as Class<EntityComponent>)
	
	
	fun eachEntityWithComponents(op: Function<Unit>, vararg components: Class<EntityComponent>) {
		if (components.isEmpty()) return
		
		entityLoop@for (entity in entities) {
			var args: ArrayList<Any?>? = null
			for (componentT in components) {
				val component = entity.findComponent(componentT)
				if (component == null) {
					continue@entityLoop
				} else {
					if (args == null) {
						args = ArrayList(components.size + 1)
						args.add(entity)
					}
					args.add(component)
				}
			}
			if (args != null) {
				unsafeInvokeFunction(op, *args.toArray())
			} else {
				unsafeInvokeFunction(op)
			}
		}
	}
	
	
	private val invokeMethods = HashMap<Class<*>, MethodHandle>()
	private fun <T> unsafeInvokeFunction(function: Function<T>, vararg args: Any?): T
		= invokeMethods.getOrPut(function::class.java, {
			var clazz: Class<*>? = function::class.java
			var out: MethodHandle? = null
			
			clazzLoop@while (clazz != null) {
				for (method in clazz.declaredMethods) {
					if (method.name == "invoke" && method.parameterCount == args.size) {
						method.isAccessible = true
						out = MethodHandles.lookup().unreflect(method)
						break@clazzLoop
					}
				}
				
				clazz = clazz.superclass
			}
			
			out!!
		}).invokeWithArguments(function, *args) as T
}
