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
	
	val nonExistentTypeCombinations: Set[(Value, Value)] = Set(
		(Fairy, Fighting)
		, (Fairy, Fire)
		, (Fairy, Ice)
		, (Ice, Steel)
	)
	
	def findTypesToExclude(typeValue: Value): Set[Value] = {
		nonExistentTypeCombinations
			.filter(_._1 == typeValue)
			.map(_._2) ++
		nonExistentTypeCombinations
			.filter(_._2 == typeValue)
			.map(_._1)
	}
	
	val nonExistentTypeManoeuvreCombinations: Set[((Value, Option[Value]), Set[Value])] = Set(
		((Fairy, Some(Electric)), Set(Bug, Fighting, Ghost, Ice))			// #702
		, ((Fairy, Some(Flying)), Set(Bug, Dark, Ice))						// #468
		, ((Fairy, Some(Grass)), Set(Bug, Fighting, Ice))					// #547
		, ((Fire, Some(Ground)), Set(Bug, Dark, Fairy, Flying, Ghost, Ice))	// #323
		, ((Fire, Some(Rock)), Set(Bug, Dark, Fairy, Flying, Ghost, Ice))	// #219
		, ((Fire, Some(Steel)), Set(Bug, Fairy, Flying, Ice))				// #485
	)
	
	def findManoeuvreTypesToExclude(firstType: Value, secondType: Option[Value]): Set[Value] = {
		nonExistentTypeManoeuvreCombinations
			.filter {
				case ((type1, type2), manoeuvreTypes) =>
					(type1 == firstType && type2 == secondType) ||
					(type2.contains(firstType) && secondType.contains(type1))
			}
			.flatMap(_._2)
	}
	
	def findTypesToExclude(otherType: Option[Value], manoeuvreTypes: Set[Value]): Set[Option[Value]] = {
		nonExistentTypeManoeuvreCombinations
			.filter {
				case ((type1, type2), excludedManoeuvreTypes) =>
					(otherType.contains(type1) || otherType == type2) &&
					manoeuvreTypes.intersect(excludedManoeuvreTypes).nonEmpty
			}
			.map {
				case ((type1, type2), _) =>
					if (otherType.contains(type1)) {
						type2
					} else {
						Some(type1)
					}
			}
	}
}
