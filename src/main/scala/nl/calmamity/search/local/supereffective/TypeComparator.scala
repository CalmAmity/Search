package nl.calmamity.search.local.supereffective

object TypeComparator {
	val typeMatrix: Map[Type.Value, Map[Type.Value, Effectiveness.Value]] = Map(
		Type.Normal -> Map(
			  Type.Rock		-> Effectiveness.NotVery
			, Type.Ghost	-> Effectiveness.DoesNotAffect
			, Type.Steel	-> Effectiveness.NotVery
		)
		, Type.Fire -> Map(
			  Type.Fire		-> Effectiveness.NotVery
			, Type.Water	-> Effectiveness.NotVery
			, Type.Grass	-> Effectiveness.Super
			, Type.Ice		-> Effectiveness.Super
			, Type.Bug		-> Effectiveness.Super
			, Type.Rock		-> Effectiveness.NotVery
			, Type.Dragon	-> Effectiveness.NotVery
			, Type.Steel	-> Effectiveness.Super
		)
		, Type.Water -> Map(
			  Type.Fire		-> Effectiveness.Super
			, Type.Water	-> Effectiveness.NotVery
			, Type.Grass	-> Effectiveness.NotVery
			, Type.Ground	-> Effectiveness.Super
			, Type.Rock		-> Effectiveness.Super
			, Type.Dragon	-> Effectiveness.NotVery
		)
		, Type.Electric -> Map(
			  Type.Water	-> Effectiveness.Super
			, Type.Electric	-> Effectiveness.NotVery
			, Type.Grass	-> Effectiveness.NotVery
			, Type.Ground	-> Effectiveness.DoesNotAffect
			, Type.Flying	-> Effectiveness.Super
			, Type.Dragon	-> Effectiveness.NotVery
		)
		, Type.Grass -> Map(
			  Type.Fire		-> Effectiveness.NotVery
			, Type.Water	-> Effectiveness.Super
			, Type.Grass	-> Effectiveness.NotVery
			, Type.Poison	-> Effectiveness.NotVery
			, Type.Ground	-> Effectiveness.Super
			, Type.Flying	-> Effectiveness.NotVery
			, Type.Bug		-> Effectiveness.NotVery
			, Type.Rock		-> Effectiveness.Super
			, Type.Dragon	-> Effectiveness.NotVery
			, Type.Steel	-> Effectiveness.NotVery
		)
		, Type.Ice -> Map(
			  Type.Fire		-> Effectiveness.NotVery
			, Type.Water	-> Effectiveness.NotVery
			, Type.Grass	-> Effectiveness.Super
			, Type.Ice		-> Effectiveness.NotVery
			, Type.Ground	-> Effectiveness.Super
			, Type.Flying	-> Effectiveness.Super
			, Type.Dragon	-> Effectiveness.Super
			, Type.Steel	-> Effectiveness.NotVery
		)
		, Type.Fighting -> Map(
			  Type.Normal	-> Effectiveness.Super
			, Type.Ice		-> Effectiveness.Super
			, Type.Poison	-> Effectiveness.NotVery
			, Type.Flying	-> Effectiveness.NotVery
			, Type.Psychic	-> Effectiveness.NotVery
			, Type.Bug		-> Effectiveness.NotVery
			, Type.Rock		-> Effectiveness.Super
			, Type.Ghost	-> Effectiveness.DoesNotAffect
			, Type.Dark		-> Effectiveness.Super
			, Type.Steel	-> Effectiveness.Super
			, Type.Fairy	-> Effectiveness.NotVery
		)
		, Type.Poison -> Map(
			  Type.Grass	-> Effectiveness.Super
			, Type.Poison	-> Effectiveness.NotVery
			, Type.Ground	-> Effectiveness.NotVery
			, Type.Rock		-> Effectiveness.NotVery
			, Type.Ghost	-> Effectiveness.NotVery
			, Type.Steel	-> Effectiveness.DoesNotAffect
			, Type.Fairy	-> Effectiveness.Super
		)
		, Type.Ground -> Map(
			  Type.Fire		-> Effectiveness.Super
			, Type.Electric	-> Effectiveness.Super
			, Type.Grass	-> Effectiveness.NotVery
			, Type.Poison	-> Effectiveness.Super
			, Type.Flying	-> Effectiveness.DoesNotAffect
			, Type.Bug		-> Effectiveness.NotVery
			, Type.Rock		-> Effectiveness.Super
			, Type.Steel	-> Effectiveness.Super
		)
		, Type.Flying -> Map(
			  Type.Electric	-> Effectiveness.NotVery
			, Type.Grass	-> Effectiveness.Super
			, Type.Fighting	-> Effectiveness.Super
			, Type.Bug		-> Effectiveness.Super
			, Type.Rock		-> Effectiveness.NotVery
			, Type.Steel	-> Effectiveness.NotVery
		)
		, Type.Psychic -> Map(
			  Type.Fighting	-> Effectiveness.Super
			, Type.Poison	-> Effectiveness.Super
			, Type.Psychic	-> Effectiveness.NotVery
			, Type.Dark		-> Effectiveness.DoesNotAffect
			, Type.Steel	-> Effectiveness.NotVery
		)
		, Type.Bug -> Map(
			  Type.Fire		-> Effectiveness.NotVery
			, Type.Grass	-> Effectiveness.Super
			, Type.Fighting	-> Effectiveness.NotVery
			, Type.Poison	-> Effectiveness.NotVery
			, Type.Flying	-> Effectiveness.NotVery
			, Type.Psychic	-> Effectiveness.Super
			, Type.Ghost	-> Effectiveness.NotVery
			, Type.Dark		-> Effectiveness.Super
			, Type.Steel	-> Effectiveness.NotVery
			, Type.Fairy	-> Effectiveness.NotVery
		)
		, Type.Rock -> Map(
			  Type.Fire		-> Effectiveness.Super
			, Type.Ice		-> Effectiveness.Super
			, Type.Fighting	-> Effectiveness.NotVery
			, Type.Ground	-> Effectiveness.NotVery
			, Type.Flying	-> Effectiveness.Super
			, Type.Bug		-> Effectiveness.Super
			, Type.Steel	-> Effectiveness.NotVery
		)
		, Type.Ghost -> Map(
			  Type.Normal	-> Effectiveness.DoesNotAffect
			, Type.Psychic	-> Effectiveness.Super
			, Type.Ghost	-> Effectiveness.Super
			, Type.Steel	-> Effectiveness.NotVery
		)
		, Type.Dragon -> Map(
			  Type.Dragon	-> Effectiveness.Super
			, Type.Steel	-> Effectiveness.NotVery
			, Type.Fairy	-> Effectiveness.DoesNotAffect
		)
		, Type.Dark -> Map(
			  Type.Fighting	-> Effectiveness.NotVery
			, Type.Psychic	-> Effectiveness.Super
			, Type.Ghost	-> Effectiveness.Super
			, Type.Dark		-> Effectiveness.NotVery
			, Type.Fairy	-> Effectiveness.NotVery
		)
		, Type.Steel -> Map(
			  Type.Fire		-> Effectiveness.NotVery
			, Type.Water	-> Effectiveness.NotVery
			, Type.Electric	-> Effectiveness.NotVery
			, Type.Ice		-> Effectiveness.Super
			, Type.Rock		-> Effectiveness.Super
			, Type.Steel	-> Effectiveness.NotVery
			, Type.Fairy	-> Effectiveness.Super
		)
		, Type.Fairy -> Map(
			  Type.Fire		-> Effectiveness.NotVery
			, Type.Fighting	-> Effectiveness.Super
			, Type.Poison	-> Effectiveness.NotVery
			, Type.Dragon	-> Effectiveness.Super
			, Type.Dark		-> Effectiveness.Super
			, Type.Steel	-> Effectiveness.NotVery
		)
	)

	def determineEffectiveness(attackingType: Type.Value, defendingType: Type.Value): Effectiveness.Value = {
		typeMatrix(attackingType).getOrElse(defendingType, Effectiveness.Normal)
	}
}
