package nl.calmamity.search.local.supereffective

import core.Action
import nl.calmamity.search.core.State

case class Team(members: Seq[SatchelCreature]) extends State[Team] {
	override def isGoalState: Boolean = false
	
	override def createAvailableActionsIterator(): Iterator[Action[Team]] = {
		// For each member, create an iterator of actions that point to a possible mutation of that member:
		val actionsPerMember = for (memberIndex <- members.indices) yield {
			// Take the member...
			members(memberIndex)
				// ...create an iterator of possible mutations...
				.createMutationsIterator()
				// ...and wrap each mutation in an action.
				.map {
					successorCreature =>
						new Action[Team](Team(members.updated(memberIndex, successorCreature)), 0)
				}
		}
		
		// Combine all iterators into one.
		actionsPerMember.reduce((iterator1, iterator2) => iterator1 ++ iterator2)
	}
	
	override def randomlySelectAvailableAction: Action[Team] = {
		val randomNumbers = new scala.util.Random()
		// Randomly determine which member is being mutated.
		val mutatedMemberIndex = randomNumbers.nextInt(members.size)
		val memberToMutate = members(mutatedMemberIndex)
		// Randomly determine which component of this member to mutate.
		val mutatedComponentIndex = randomNumbers.nextInt(SatchelCreature.numComponents)
		// Determine the available alternative types for this component.
		val typeAlternativesForComponent = memberToMutate.determineTypeAlternatives(mutatedComponentIndex)
		val mutationResult = memberToMutate.createMutation(
			mutatedComponentIndex
			, typeAlternativesForComponent
			, randomNumbers.nextInt(typeAlternativesForComponent.size)
		)
		val membersOfNewTeam = members.updated(mutatedMemberIndex, mutationResult)
		new Action[Team](Team(membersOfNewTeam), 0)
	}
	
	override def toString: String = s"Team(${members.mkString(", ")})"
	
	override def equals(other: scala.Any): Boolean = other match {
		case otherTeam: Team =>
			this.members.toSet == otherTeam.members.toSet
		case _ =>
			false
	}
}

object Team {
	def apply(numMembers: Int): Team = {
		Team(for (_ <- 1 to numMembers) yield SatchelCreature())
	}
}