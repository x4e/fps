package dev.binclub.fps.shared.state

/**
 * Represents an update to the gamestate
 *
 * Game state updates are linked lists that can be traversed
 *
 * @author cookiedragon234 12/Jul/2020
 */
abstract class GameStateUpdate {
	abstract fun apply(gameState: GameState)
	abstract fun reverse(gameState: GameState)
}
