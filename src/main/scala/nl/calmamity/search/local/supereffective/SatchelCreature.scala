package nl.calmamity.search.local.supereffective

/**
  * Represents one of the Satchel Creatures.
  * @param firstType The type of the creature. Each creature has at least one type.
  * @param secondType The optional second type of the creature. Each creature has at most two types.
  * @param manoeuvreTypes The types of the individual manoeuvres the creature can perform. Each creature can have at
  * most four manoeuvres available to it, and because having fewer manoeuvres available confers no advantage, we model
  * creatures as always having exactly four available.
  */
case class SatchelCreature(
	firstType: Type.Value
	, secondType: Option[Type.Value]
	, manoeuvreTypes: (Type.Value, Type.Value, Type.Value, Type.Value)
) {
	def createMutationsIterator(): Iterator[SatchelCreature] = new Iterator[SatchelCreature]() {
		var currentComponentIndex = 0
		var alternateTypeValuesForCurrentComponent: Seq[Option[Type.Value]] =
			determineTypeAlternatives(currentComponentIndex)
		
		var typeIndex = 0
		
		override def hasNext: Boolean = currentComponentIndex < SatchelCreature.numComponents
		
		override def next(): SatchelCreature = {
			val newCreature = createMutation(currentComponentIndex, alternateTypeValuesForCurrentComponent, typeIndex)
			
			typeIndex += 1
			if (typeIndex >= alternateTypeValuesForCurrentComponent.size) {
				typeIndex = 0
				currentComponentIndex += 1
				if (currentComponentIndex < SatchelCreature.numComponents) {
					alternateTypeValuesForCurrentComponent = determineTypeAlternatives(currentComponentIndex)
				}
			}
			
			newCreature
		}
	}
	
	def createMutation(componentToMutateIndex: Int, typeOptions: Seq[Option[Type.Value]], typeIndex: Int): SatchelCreature = {
		def replaceWithAlternativeTypeIfNecessary(componentIndex: Int, currentType: Option[Type.Value]): Option[Type.Value] = {
			if (componentIndex == componentToMutateIndex) {
				typeOptions(typeIndex)
			} else {
				currentType
			}
		}
		
		SatchelCreature(
			replaceWithAlternativeTypeIfNecessary(0, Some(firstType)).get
			, replaceWithAlternativeTypeIfNecessary(SatchelCreature.secondTypeIndex, secondType)
			, (
				replaceWithAlternativeTypeIfNecessary(2, Some(manoeuvreTypes._1)).get
				, replaceWithAlternativeTypeIfNecessary(3, Some(manoeuvreTypes._2)).get
				, replaceWithAlternativeTypeIfNecessary(4, Some(manoeuvreTypes._3)).get
				, replaceWithAlternativeTypeIfNecessary(5, Some(manoeuvreTypes._4)).get
			)
		)
	}
	
	def determineTypeAlternatives(currentComponentIndex: Int): Seq[Option[Type.Value]] = {
		((Type.values.map(Option.apply) + None) -- typesToExclude(currentComponentIndex)).toSeq
	}
	
	def typesToExclude(componentIndex: Int): Seq[Option[Type.Value]] = {
		componentIndex match {
			case SatchelCreature.secondTypeIndex =>
				Seq(Some(firstType), secondType)
			case _ =>
				Seq(None, determineComponentType(componentIndex))
		}
	}
	
	def determineComponentType(componentIndex: Int): Option[Type.Value] = {
		componentIndex match {
			case 0 =>
				Some(firstType)
			case SatchelCreature.secondTypeIndex =>
				secondType
			case 2 =>
				Some(manoeuvreTypes._1)
			case 3 =>
				Some(manoeuvreTypes._2)
			case 4 =>
				Some(manoeuvreTypes._3)
			case 5 =>
				Some(manoeuvreTypes._4)
		}
	}
}

object SatchelCreature {
	val numComponents = 6
	val secondTypeIndex = 1
}
