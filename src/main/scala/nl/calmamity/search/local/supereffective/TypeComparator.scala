package nl.calmamity.search.local.supereffective

object TypeComparator {
	val typeMatrix: Map[Type.Value, Map[Type.Value, Effectiveness]] = Map(
		Type.Normal -> Map(
			  Type.Rock		-> NotVery
			, Type.Ghost	-> DoesNotAffect
			, Type.Steel	-> NotVery
		)
		, Type.Fire -> Map(
			  Type.Fire		-> NotVery
			, Type.Water	-> NotVery
			, Type.Grass	-> Super
			, Type.Ice		-> Super
			, Type.Bug		-> Super
			, Type.Rock		-> NotVery
			, Type.Dragon	-> NotVery
			, Type.Steel	-> Super
		)
		, Type.Water -> Map(
			  Type.Fire		-> Super
			, Type.Water	-> NotVery
			, Type.Grass	-> NotVery
			, Type.Ground	-> Super
			, Type.Rock		-> Super
			, Type.Dragon	-> NotVery
		)
		, Type.Electric -> Map(
			  Type.Water	-> Super
			, Type.Electric	-> NotVery
			, Type.Grass	-> NotVery
			, Type.Ground	-> DoesNotAffect
			, Type.Flying	-> Super
			, Type.Dragon	-> NotVery
		)
		, Type.Grass -> Map(
			  Type.Fire		-> NotVery
			, Type.Water	-> Super
			, Type.Grass	-> NotVery
			, Type.Poison	-> NotVery
			, Type.Ground	-> Super
			, Type.Flying	-> NotVery
			, Type.Bug		-> NotVery
			, Type.Rock		-> Super
			, Type.Dragon	-> NotVery
			, Type.Steel	-> NotVery
		)
		, Type.Ice -> Map(
			  Type.Fire		-> NotVery
			, Type.Water	-> NotVery
			, Type.Grass	-> Super
			, Type.Ice		-> NotVery
			, Type.Ground	-> Super
			, Type.Flying	-> Super
			, Type.Dragon	-> Super
			, Type.Steel	-> NotVery
		)
		, Type.Fighting -> Map(
			  Type.Normal	-> Super
			, Type.Ice		-> Super
			, Type.Poison	-> NotVery
			, Type.Flying	-> NotVery
			, Type.Psychic	-> NotVery
			, Type.Bug		-> NotVery
			, Type.Rock		-> Super
			, Type.Ghost	-> DoesNotAffect
			, Type.Dark		-> Super
			, Type.Steel	-> Super
			, Type.Fairy	-> NotVery
		)
		, Type.Poison -> Map(
			  Type.Grass	-> Super
			, Type.Poison	-> NotVery
			, Type.Ground	-> NotVery
			, Type.Rock		-> NotVery
			, Type.Ghost	-> NotVery
			, Type.Steel	-> DoesNotAffect
			, Type.Fairy	-> Super
		)
		, Type.Ground -> Map(
			  Type.Fire		-> Super
			, Type.Electric	-> Super
			, Type.Grass	-> NotVery
			, Type.Poison	-> Super
			, Type.Flying	-> DoesNotAffect
			, Type.Bug		-> NotVery
			, Type.Rock		-> Super
			, Type.Steel	-> Super
		)
		, Type.Flying -> Map(
			  Type.Electric	-> NotVery
			, Type.Grass	-> Super
			, Type.Fighting	-> Super
			, Type.Bug		-> Super
			, Type.Rock		-> NotVery
			, Type.Steel	-> NotVery
		)
		, Type.Psychic -> Map(
			  Type.Fighting	-> Super
			, Type.Poison	-> Super
			, Type.Psychic	-> NotVery
			, Type.Dark		-> DoesNotAffect
			, Type.Steel	-> NotVery
		)
		, Type.Bug -> Map(
			  Type.Fire		-> NotVery
			, Type.Grass	-> Super
			, Type.Fighting	-> NotVery
			, Type.Poison	-> NotVery
			, Type.Flying	-> NotVery
			, Type.Psychic	-> Super
			, Type.Ghost	-> NotVery
			, Type.Dark		-> Super
			, Type.Steel	-> NotVery
			, Type.Fairy	-> NotVery
		)
		, Type.Rock -> Map(
			  Type.Fire		-> Super
			, Type.Ice		-> Super
			, Type.Fighting	-> NotVery
			, Type.Ground	-> NotVery
			, Type.Flying	-> Super
			, Type.Bug		-> Super
			, Type.Steel	-> NotVery
		)
		, Type.Ghost -> Map(
			  Type.Normal	-> DoesNotAffect
			, Type.Psychic	-> Super
			, Type.Ghost	-> Super
			, Type.Steel	-> NotVery
		)
		, Type.Dragon -> Map(
			  Type.Dragon	-> Super
			, Type.Steel	-> NotVery
			, Type.Fairy	-> DoesNotAffect
		)
		, Type.Dark -> Map(
			  Type.Fighting	-> NotVery
			, Type.Psychic	-> Super
			, Type.Ghost	-> Super
			, Type.Dark		-> NotVery
			, Type.Fairy	-> NotVery
		)
		, Type.Steel -> Map(
			  Type.Fire		-> NotVery
			, Type.Water	-> NotVery
			, Type.Electric	-> NotVery
			, Type.Ice		-> Super
			, Type.Rock		-> Super
			, Type.Steel	-> NotVery
			, Type.Fairy	-> Super
		)
		, Type.Fairy -> Map(
			  Type.Fire		-> NotVery
			, Type.Fighting	-> Super
			, Type.Poison	-> NotVery
			, Type.Dragon	-> Super
			, Type.Dark		-> Super
			, Type.Steel	-> NotVery
		)
	)

	def determineEffectiveness(attackingType: Type.Value, defendingType: Type.Value): Effectiveness = {
		typeMatrix(attackingType).getOrElse(defendingType, Regular)
	}
}
