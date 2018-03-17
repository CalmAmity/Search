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
		(Fairy, Bug)
		, (Fairy, Dark)
		, (Fairy, Fighting)
		, (Fairy, Fire)
		, (Fairy, Ground)
		, (Fairy, Ice)
		, (Fighting, Ground)
		, (Fire, Ice)
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
		((Bug, Some(Fighting)), Set(Electric, Fairy, Fire, Ice, Poison, Steel, Water))								// #214
		, ((Bug, Some(Rock)), Set(Electric, Fairy, Fire, Ice))														// #213/348/558
		, ((Dark, Some(Ice)), Set(Electric, Fairy, Fire, Rock))														// #461
		, ((Dragon, Some(Fairy)), Set(Bug, Electric, Poison, Rock, Water))											// #334
		, ((Electric, Some(Fairy)), Set(Bug, Fighting, Fire, Ghost, Ice, Poison, Water))							// #702
		, ((Electric, Some(Flying)), Set(Bug, Fairy, Ice, Water))													// #145/479/642/587
		, ((Electric, Some(Steel)), Set(Bug, Dark, Fairy, Fighting, Fire, Flying, Ghost, Ice, Poison, Rock, Water))	// #462
		, ((Fairy, Some(Flying)), Set(Bug, Dark, Electric, Ice, Poison, Water))										// #468
		, ((Fairy, Some(Grass)), Set(Bug, Electric, Fighting, Fire, Ice, Poison, Water))							// #547
		, ((Fairy, Some(Rock)), Set(Bug, Dark, Electric, Fighting, Fire, Flying, Ghost, Ice, Poison, Water))		// #703/719
		, ((Fairy, Some(Steel)), Set(Bug, Flying, Water))															// #303/707
		, ((Fighting, Some(Fire)), Set(Bug, Fairy, Ice))															// #257/392/500
		, ((Fighting, Some(Flying)), Set(Electric, Fairy, Fire, Ghost, Ice, Water))									// #701
		, ((Fighting, Some(Grass)), Set(Electric, Fairy, Fire, Ice, Water))											// #286/640/652
		, ((Fighting, Some(Water)), Set(Electric, Fairy, Fire, Grass, Ghost))										// #062/645
		, ((Fire, Some(Flying)), Set(Bug, Fairy, Ice, Poison, Water))												// #006/146/250/663
		, ((Fire, Some(Ground)), Set(Bug, Dark, Electric, Fairy, Flying, Ghost, Ice, Poison, Water))				// #323
		, ((Fire, Some(Rock)), Set(Bug, Dark, Electric, Fairy, Flying, Ghost, Ice, Water))							// #219
		, ((Fire, Some(Steel)), Set(Bug, Electric, Fairy, Flying, Ice, Poison, Water))								// #485
		, ((Flying, Some(Ice)), Set(Bug, Electric, Fairy, Fire, Ghost, Grass, Poison, Water))						// #144/225
		, ((Grass, Some(Steel)), Set(Fire, Ice, Water))																// #598
		, ((Grass, Some(Ice)), Set(Bug, Dark, Electric, Fairy, Fire, Flying, Poison, Water))						// #460
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
