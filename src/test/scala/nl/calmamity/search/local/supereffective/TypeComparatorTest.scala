package nl.calmamity.search.local.supereffective

import org.scalatest.FlatSpec

class TypeComparatorTest extends FlatSpec {
	"determineEffectiveness" should "determine effectiveness correctly" in {
		assertResult(Type.Normal	, Type.Ghost	, Effectiveness.DoesNotAffect)
		assertResult(Type.Fire		, Type.Bug		, Effectiveness.Super)
		assertResult(Type.Water		, Type.Dragon	, Effectiveness.NotVery)
		assertResult(Type.Electric	, Type.Ground	, Effectiveness.DoesNotAffect)
		assertResult(Type.Grass		, Type.Ground	, Effectiveness.Super)
		assertResult(Type.Ice		, Type.Steel	, Effectiveness.NotVery)
		assertResult(Type.Fighting	, Type.Ghost	, Effectiveness.DoesNotAffect)
		assertResult(Type.Poison	, Type.Steel	, Effectiveness.DoesNotAffect)
		assertResult(Type.Ground	, Type.Flying	, Effectiveness.DoesNotAffect)
		assertResult(Type.Flying	, Type.Bug		, Effectiveness.Super)
		assertResult(Type.Psychic	, Type.Dark		, Effectiveness.DoesNotAffect)
		assertResult(Type.Bug		, Type.Fairy	, Effectiveness.NotVery)
		assertResult(Type.Rock		, Type.Ice		, Effectiveness.Super)
		assertResult(Type.Ghost		, Type.Normal	, Effectiveness.DoesNotAffect)
		assertResult(Type.Dragon	, Type.Fairy	, Effectiveness.DoesNotAffect)
		assertResult(Type.Dark		, Type.Ghost	, Effectiveness.Super)
		assertResult(Type.Steel		, Type.Electric	, Effectiveness.NotVery)
		assertResult(Type.Fairy		, Type.Fire		, Effectiveness.NotVery)
	}

	def assertResult(attackingType: Type.Value, defendingType: Type.Value, expected: Effectiveness.Value): Unit = {
		assert(TypeComparator.determineEffectiveness(attackingType, defendingType) == expected)
	}
}
