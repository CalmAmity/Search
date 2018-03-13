package nl.calmamity.search.core

import core.Action
import nl.calmamity.search.util.IdentifierCache

/** Defines the basic methods shared by states for all problems. */
	/** The state class implementing this trait. */
trait State[ImplementingType <: State[ImplementingType]] {
//	// Specify that the implementing type must extend itself, to prevent references to the wrong State type.
//	self: ImplementingType =>
	
	val stateId: Long = IdentifierCache.createStateId
	
	/** @return `true` if this state is a goal state according to the definition of the problem; `false` otherwise. */
	def isGoalState: Boolean

	/** @return an iterator that produces all actions available from this state exactly ones. */
	def createAvailableActionsIterator(): Iterator[Action[ImplementingType]]

	/**
	  * @return one of the actions available in the current state, randomly selected from all available actions.
	  * Multiple consecutive calls to this method may produce the same action multiple times.
	  */
	def randomlySelectAvailableAction: Action[ImplementingType]
}
