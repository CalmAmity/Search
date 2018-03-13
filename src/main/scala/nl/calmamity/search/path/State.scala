package nl.calmamity.search.path

import nl.calmamity.search.core

/** Defines shared methods for states in path search algorithms. */
trait State[ImplementingType <: State[ImplementingType]] extends core.State[ImplementingType] {
	/** The state immediately preceding this one in the path currently being explored. */
	val predecessor: Option[ImplementingType]
	
	/** The cost of reaching this state from the starting state along the path of predecessors. */
	val cost: Double
}
