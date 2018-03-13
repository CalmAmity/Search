package nl.calmamity.search.local.supereffective

import org.scalatest.FlatSpec

class SatchelCreatureTest extends FlatSpec {
	"morph" should "produce all possible mutations of a creature" in {
		val singleTypeCreature = SatchelCreature(Type.Bug, None, (Type.Bug, Type.Dark, Type.Dragon, Type.Electric))
		val morphResults1 = singleTypeCreature.createMutationsIterator().toList
		// The list of derived creatures should have 17 * 6 = 102 elements.
		assert(morphResults1.size == 17 * 6)
		// Check that no duplicate elements are among the results.
		assert(morphResults1.distinct.size == 17 * 6)
		// The list should not contain the original creature.
		assert(!morphResults1.contains(singleTypeCreature))
		
		// Perform the same tests with a dual-type creature.
		val dualTypeCreature =
			SatchelCreature(Type.Fairy, Some(Type.Fighting), (Type.Flying, Type.Fire, Type.Ghost, Type.Grass))
		val morphResults2 = dualTypeCreature.createMutationsIterator().toList
		assert(morphResults2.size == 17 * 6)
		assert(morphResults2.distinct.size == 17 * 6)
		assert(!morphResults2.contains(singleTypeCreature))
	}
}
