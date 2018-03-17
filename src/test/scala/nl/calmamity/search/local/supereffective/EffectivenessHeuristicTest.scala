package nl.calmamity.search.local.supereffective

import nl.calmamity.search.local.supereffective.Type._
import org.scalatest.FlatSpec

class EffectivenessHeuristicTest extends FlatSpec {
	"allTypeCombinations" should "exclude non-existent type combinations" in {
		val typeCombinations = new EffectivenessHeuristic().allTypeCombinations
		val expectedSize = math.pow(Type.values.size, 2) - (Type.nonExistentTypeCombinations.size * 2)
		assert(typeCombinations.size == expectedSize)
	}
	
	"estimateQualityScore" should "correctly determine the quality score of a team" in {
		val heuristic = new EffectivenessHeuristic()
		val team = Team(Seq(
			SatchelCreature(Rock, Steel, Flying, Steel, Rock, Ice)
			, SatchelCreature(Grass, Fairy, Grass, Fairy, Dark, Bug)
			, SatchelCreature(Ground, Fire, Ground, Fire, Fighting, Fire)
		))
		assert(heuristic.determineQualityScore(team) == 903)
	}
}
