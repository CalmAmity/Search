package nl.calmamity.search.local.supereffective

sealed trait Effectiveness {
	val damageFactor: Double
}

case object Super extends Effectiveness {
	override val damageFactor: Double = 2
}

case object Regular extends Effectiveness {
	override val damageFactor: Double = 1
}

case object NotVery extends Effectiveness {
	override val damageFactor: Double = .5
}

case object DoesNotAffect extends Effectiveness {
	override val damageFactor: Double = 0
}
