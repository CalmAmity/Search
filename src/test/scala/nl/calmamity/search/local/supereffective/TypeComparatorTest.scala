package nl.calmamity.search.local.supereffective

import org.scalatest.FlatSpec

class TypeComparatorTest extends FlatSpec {
	"determineEffectiveness" should "determine effectiveness correctly" in {
		assertResult(Type.Normal	, Type.Ghost	, DoesNotAffect)
		assertResult(Type.Fire		, Type.Bug		, Super)
		assertResult(Type.Water		, Type.Dragon	, NotVery)
		assertResult(Type.Electric	, Type.Ground	, DoesNotAffect)
		assertResult(Type.Grass		, Type.Ground	, Super)
		assertResult(Type.Ice		, Type.Steel	, NotVery)
		assertResult(Type.Fighting	, Type.Ghost	, DoesNotAffect)
		assertResult(Type.Poison	, Type.Steel	, DoesNotAffect)
		assertResult(Type.Ground	, Type.Flying	, DoesNotAffect)
		assertResult(Type.Flying	, Type.Bug		, Super)
		assertResult(Type.Psychic	, Type.Dark		, DoesNotAffect)
		assertResult(Type.Bug		, Type.Fairy	, NotVery)
		assertResult(Type.Rock		, Type.Ice		, Super)
		assertResult(Type.Ghost		, Type.Normal	, DoesNotAffect)
		assertResult(Type.Dragon	, Type.Fairy	, DoesNotAffect)
		assertResult(Type.Dark		, Type.Ghost	, Super)
		assertResult(Type.Steel		, Type.Electric	, NotVery)
		assertResult(Type.Fairy		, Type.Fire		, NotVery)
	}

	def assertResult(attackingType: Type.Value, defendingType: Type.Value, expected: Effectiveness): Unit = {
		assert(TypeComparator.determineEffectiveness(attackingType, defendingType) == expected)
	}
}
