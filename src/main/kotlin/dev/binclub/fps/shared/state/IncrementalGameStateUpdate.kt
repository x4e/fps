package dev.binclub.fps.shared.state

/**
 * @author cookiedragon234 12/Jul/2020
 */
class IncrementalGameStateUpdate: GameStateUpdate() {
	var previous: GameStateUpdate? = null
	var next: GameStateUpdate? = null
	
	/**
	 * Applies this update to the current gamestate
	 */
	override fun apply(gameState: GameState) {
		if (gameState.currentUpdate != previous) {
			error("Gamestate can only be applied incrementally")
		}
	}
	
	/**
	 * Reverses this update from the current gamestate
	 */
	override fun reverse(gameState: GameState) {
		if (gameState.currentUpdate != this) {
			error("Gamesta  te can only be applied incrementally")
		}
	}
}
