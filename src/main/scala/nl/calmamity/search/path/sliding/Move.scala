package nl.calmamity.search.path.sliding

/**
  * Defines the possible moves in a sliding puzzle. In any state, at most one tile can slide in a given direction, which
  * means that only the direction is required to define the move.
  */
object Move extends Enumeration {
	val SlideUp, SlideDown, SlideLeft, SlideRight = Value
	
	def determineRelativePosition(move: Value): (Int, Int) = {
		move match {
			case SlideUp =>
				(0, 1)
			case SlideDown =>
				(0, -1)
			case SlideLeft =>
				(1, 0)
			case SlideRight =>
				(-1, 0)
		}
	}
}
