package nl.calmamity.search.local.supereffective

object Type extends Enumeration {
	val
		Normal
		, Fire
		, Water
		, Electric
		, Grass
		, Ice
		, Fighting
		, Poison
		, Ground
		, Flying
		, Psychic
		, Bug
		, Rock
		, Ghost
		, Dragon
		, Dark
		, Steel
		, Fairy
		= Value
	
	val nonExistentTypeCombinations: Map[Value, Set[Value]] = Map(
		Ice -> Set(Fairy)
		, Fairy -> Set(Ice)
	)
}
