@file:Suppress("UNUSED_PARAMETER")

package dev.binclub.fps.shared.state

/**
 * @author cookiedragon234 12/Jul/2020
 */
class DifferentialGameStateUpdate (
	gameState: GameState // The game state to differentiate
): GameStateUpdate() {
	override fun apply(gameState: GameState) {
	}
	
	override fun reverse(gameState: GameState) {
	}
}
