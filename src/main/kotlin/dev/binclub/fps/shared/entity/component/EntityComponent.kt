package dev.binclub.fps.shared.entity.component

import dev.binclub.fps.shared.entity.Entity
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * @author cookiedragon234 09/Jul/2020
 */
abstract class EntityComponent {
	var entity by nonNull<Entity> (::attachTo)
	
	protected open fun attachTo(entity: Entity) {}
	
	private fun <T: Any> nonNull(onChange: (T) -> Unit): NonNull<T> = NonNull(onChange)
	
	private class NonNull<T : Any>(val onChange: (T) -> Unit) {
		private var value: T? = null
		
		operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
			return value ?: throw IllegalStateException("Property ${property.name} should be initialized before get.")
		}
		
		operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
			this.value = value
			if (value != null) {
				onChange(value)
			}
		}
	}
}
