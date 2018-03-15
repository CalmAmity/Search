package nl.calmamity.search.local.supereffective

import org.scalatest.FlatSpec
import Type._

class TeamTest extends FlatSpec {
	"randomlySelectAvailableAction" should "create a different team" in {
		val originalTeam = Team(Seq(
			SatchelCreature(Type.Dragon, Some(Type.Ground), Seq(Type.Electric, Type.Fairy, Type.Fighting, Type.Flying))
			, SatchelCreature(Type.Grass, None, Seq(Type.Grass, Type.Water, Type.Ghost, Type.Fire))
		))
		for (_ <- 1 to 1000) {
			val mutatedTeam = originalTeam.randomlySelectAvailableAction.getResultingState
			assert(mutatedTeam.members.size == originalTeam.members.size)
			assert(determineNumTypeDifferences(originalTeam, mutatedTeam) == 1)
		}
	}
	
	def determineNumTypeDifferences(team1: Team, team2: Team): Int = {
		team1.members.zip(team2.members)
			.map {
				case (creature1, creature2) =>
					determineNumTypeDifferences(creature1, creature2)
			}
			.sum
	}
	
	def determineNumTypeDifferences(creature1: SatchelCreature, creature2: SatchelCreature): Int = {
		val typePairs = for (componentIndex <- 0 until SatchelCreature.numComponents) yield
			(creature1.determineComponentType(componentIndex), creature2.determineComponentType(componentIndex))
		
		typePairs.count {
			case (type1, type2) =>
				type1 != type2
		}
	}
	
	"equals" should "properly detect equality" in {
		val team1 = Team(Seq(
			SatchelCreature(Water, Some(Steel), Seq(Grass, Fighting, Fairy, Ice))
			, SatchelCreature(Fighting, Some(Normal), Seq(Normal, Ground, Flying, Bug))
		))
		val team2 = Team(Seq(
			SatchelCreature(Normal, Some(Fighting), Seq(Ground, Flying, Normal, Bug))
			, SatchelCreature(Steel, Some(Water), Seq(Fairy, Ice, Fighting, Grass))
		))
		assert(team1 == team2)
		val team3 = Team(Seq(
			SatchelCreature(Water, Some(Steel), Seq(Grass, Fighting, Fairy, Ice))
			, SatchelCreature(Fighting, None, Seq(Normal, Ground, Flying, Bug))
		))
		assert(team1 != team3)
	}
}
