package nl.calmamity.search.local.supereffective

import nl.calmamity.search.local.supereffective.Type._
import org.scalatest.FlatSpec

class SatchelCreatureTest extends FlatSpec {
	"morph" should "produce all possible mutations of a creature" in {
		val singleTypeCreature = SatchelCreature(Type.Bug, None, Seq(Type.Bug, Type.Dark, Type.Dragon, Type.Electric))
		val morphResults1 = singleTypeCreature.createMutationsIterator().toList
		// The list of derived creatures should have 17 * 6 = 102 elements.
		assert(morphResults1.size == 17 * 6)
		// Check that no duplicate elements are among the results.
		assert(morphResults1.distinct.size == 17 * 6)
		// The list should not contain the original creature.
		assert(!morphResults1.contains(singleTypeCreature))
		
		// Perform the same tests with a dual-type creature.
		val dualTypeCreature =
			SatchelCreature(Type.Fairy, Some(Type.Fighting), Seq(Type.Flying, Type.Fire, Type.Ghost, Type.Grass))
		val morphResults2 = dualTypeCreature.createMutationsIterator().toList
		assert(morphResults2.size == 17 * 6)
		assert(morphResults2.distinct.size == 17 * 6)
		assert(!morphResults2.contains(singleTypeCreature))
	}
	
	it should "not produce mutations with non-existent type combinations" in {
		val creature1 = SatchelCreature(Ice, None, Seq(Fairy, Electric, Bug, Ice))
		val morphResults1 = creature1.createMutationsIterator().toList
		assert(!morphResults1.exists{
			successorCreature =>
				successorCreature.firstType == Ice && successorCreature.secondType.contains(Fairy)
		})
		
		val creature2 = SatchelCreature(Ground, Some(Fairy), Seq(Fairy, Electric, Bug, Ice))
		val morphResults2 = creature2.createMutationsIterator().toList
		assert(!morphResults2.exists{
			successorCreature =>
				successorCreature.firstType == Ice && successorCreature.secondType.contains(Fairy)
		})
	}
	
	it should "not produce mutations with unavailable manoeuvre types" in {
		val creature1 = SatchelCreature(Fairy, Flying, Normal, Normal, Normal, Normal)
		val morphResults1 = creature1.createMutationsIterator().toList
		assert(!morphResults1.exists{
			successorCreature =>
				successorCreature.manoeuvreTypes.contains(Ice)
		})
		
		val creature2 = SatchelCreature(Flying, Fairy, Normal, Normal, Normal, Normal)
		val morphResults2 = creature2.createMutationsIterator().toList
		assert(!morphResults2.exists{
			successorCreature =>
				successorCreature.manoeuvreTypes.contains(Ice)
		})
		
		
		val creature3 = SatchelCreature(Normal, Flying, Ice, Normal, Normal, Normal)
		val morphResults3 = creature3.createMutationsIterator().toList
		assert(!morphResults3.exists(_.firstType == Fairy))
		
		val creature4 = SatchelCreature(Fairy, Normal, Ice, Normal, Normal, Normal)
		val morphResults4 = creature4.createMutationsIterator().toList
		assert(!morphResults4.exists(_.secondType.contains(Flying)))
	}
	
	"equals" should "properly detect equality" in {
		val creature1 = SatchelCreature(Ghost, Some(Grass), Seq(Steel, Fighting, Grass, Ghost))
		val creature2 = SatchelCreature(Grass, Some(Ghost), Seq(Fighting, Ghost, Grass, Steel))
		assert(creature1 == creature2)
		val creature3 = SatchelCreature(Ghost, None, Seq(Steel, Fighting, Grass, Ghost))
		assert(creature1 != creature3)
	}
}
