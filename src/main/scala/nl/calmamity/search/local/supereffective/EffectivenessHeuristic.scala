package nl.calmamity.search.local.supereffective

import nl.calmamity.search.core.Heuristic

class EffectivenessHeuristic extends Heuristic[Team] {
	override val optimalScore: Double = Double.PositiveInfinity
	
	val allTypeCombinations: Seq[(Type.Value, Option[Type.Value])] =
		Type.values.toSeq
        	.flatMap {
				typeValue =>
					val allOtherTypes = (Type.values - typeValue).map(Option.apply) + None
					allOtherTypes.toSeq.map {
						otherType =>
							(typeValue, otherType)
					}
			}
	
	override def estimateQualityScore(team: Team): Double = {
		team.members
			.flatMap {
				member =>
					allTypeCombinations.map {
						typeCombination =>
							determineEffectivenessScore(member, typeCombination)
					}
			}
			.sum
	}
	
	def determineEffectivenessScore(creature: SatchelCreature, opposingTypes: (Type.Value, Option[Type.Value])): Double = {
		// Determine the effectiveness of the best move the creature has for the current opponent. For each available
		// manoeuvre:
		val bestOutgoingManoeuvreFactor = creature.manoeuvreTypes
			.map {
				manoeuvreType =>
					// Determine the damage factor for this manoeuvre when used against the indicated opponent.
					val effectiveness = determineDamageFactorForManoeuvre(manoeuvreType, opposingTypes)
					if (creature.firstType == manoeuvreType || creature.secondType.contains(manoeuvreType)) {
						// The type of the manoeuvre matches one of the types of the creatures performing it. This means
						// the move is subject to a damage increase.
						effectiveness * EffectivenessHeuristic.sameTypeDamageFactor
					} else {
						effectiveness
					}
			}
			// Retain the damage factor for the most effective manoeuvre.
			.max
		
		val typesOfIncomingManoeuvres = opposingTypes._1 +: opposingTypes._2.toSeq
		val incomingFactor = typesOfIncomingManoeuvres
			.map {
				manoeuvreType =>
					determineDamageFactorForManoeuvre(manoeuvreType, (creature.firstType, creature.secondType))
			}
			.max
		
		(bestOutgoingManoeuvreFactor * EffectivenessHeuristic.attackDefenseWeighingFactor) - incomingFactor
	}
	
	def determineDamageFactorForManoeuvre(manoeuvreType: Type.Value, targetTypes: (Type.Value, Option[Type.Value])): Double = {
		val firstTypeEffectiveness = TypeComparator.determineEffectiveness(manoeuvreType, targetTypes._1)
		val secondTypeEffectiveness = targetTypes._2
			.map {
				secondType =>
					TypeComparator.determineEffectiveness(manoeuvreType, secondType)
			}
			.getOrElse(Regular)
		
		firstTypeEffectiveness.damageFactor * secondTypeEffectiveness.damageFactor
	}
}

object EffectivenessHeuristic {
	val attackDefenseWeighingFactor: Double = 1
	val sameTypeDamageFactor: Double = 1.5
}
