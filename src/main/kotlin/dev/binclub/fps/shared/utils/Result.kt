package dev.binclub.fps.shared.utils

/**
 * @author cookiedragon234 09/Jul/2020
 */
class Result private constructor (val success: Boolean) {
	companion object {
		val SUCCESS = Result(true)
		val FAILURE = Result(false)
	}
	
	inline infix fun `else`(op: () -> Unit) {
		if (!success) {
			op()
		}
	}
}
